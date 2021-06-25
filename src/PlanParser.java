import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PlanParser {
    private DocumentBuilder builder;
    private String[] sharedStrings;
    private NodeList cellNodes;

    public Plan parse(String file) throws IOException, SAXException, ParserConfigurationException {
        Document document;
        Plan plan = new Plan();

        ZipFile zipFile = new ZipFile(file);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        // Парсинг текстовых строк из файла xl/sharedStrings.xml
        sharedStrings = parseSharedStrings(zipFile);

        ZipEntry zipEntry = zipFile.getEntry("xl/worksheets/sheet1.xml");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        document = builder.parse(inputStream);
        cellNodes = document.getElementsByTagName("c");

        plan.setTitle(getCellStringValue("C5"));
        plan.setSemester(getCellIntValue("E7"));
        plan.setFaculty(getCellStringValue("K7"));
        plan.setDirection(getCellStringValue("T7"));

        // Парсинг дисциплин
        int i = 12;
        while(true){
            Subject subject = parseSubject("A" + i);
            i += 4;
            if(subject == null){
                break;
            } else {
                plan.addSubject(subject);
            }
        }

        // Парсинг факультативов
        i++;
        while(true){
            Subject subject = parseSubject("A" + i);
            i += 4;
            if(subject == null){
                break;
            } else {
                plan.addElective(subject);
            }
        }
        return plan;
    }

    private String[] parseSharedStrings(ZipFile file) throws IOException, SAXException {
        ZipEntry zipEntry = file.getEntry("xl/sharedStrings.xml");
        InputStream inputStream = file.getInputStream(zipEntry);

        Document document = builder.parse(inputStream);
        NodeList nodes = document.getElementsByTagName("t");
        String[] sharedStrings = new String[nodes.getLength()];
        for(int i = 0; i < nodes.getLength(); i++){
            sharedStrings[i] = nodes.item(i).getTextContent();
        }
        return sharedStrings;
    }

    // Метод протестирован только на ячейках без формул
    private String getCellStringValue(String coordinates){
        for(int i = 0; i < cellNodes.getLength(); i++){
            NamedNodeMap attributes = cellNodes.item(i).getAttributes();
            if(attributes.getNamedItem("r").getTextContent().equals(coordinates)){
                Node node = cellNodes.item(i);
                if(attributes.getNamedItem("t") != null && attributes.getNamedItem("t").getTextContent().equals("s")){
                    int posAtSharedStrings = Integer.parseInt(node.getTextContent());
                    return sharedStrings[posAtSharedStrings];
                } else {
                    return node.getTextContent();
                }
            }
        }
        return "";
    }

    private int getCellIntValue(String coordinates){
        String value = getCellStringValue(coordinates);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e){
            return 0;
        }
    }

    private Subject parseSubject(String coordinates){
        char col = coordinates.charAt(0);
        int row = Integer.parseInt(coordinates.substring(1));
        // Проверка на незаполненные дисциплины (по отсутствию номера п/п или названия)
        if(getCellIntValue(coordinates) == 0 || getCellStringValue(Character.toString(col + 1) + row).equals("")){
            return null;
        }
        Subject subject = new Subject();
        String[] title = getCellStringValue(Character.toString(col + 1) + row).split("\\(");
        subject.setName(title[0].trim());
        subject.setCathedra(title[1].trim().substring(0, title[1].length() - 1));
        int[] lectures = new int[18];
        int[] labs = new int[18];
        int[] practices = new int[18];
        int[] individual = new int[18];
        int sum = 0;
        for(int i = 0; i < 18; i++){
            lectures[i] = getCellIntValue(Character.toString(col + 4 + i) + row);
            labs[i] = getCellIntValue(Character.toString(col + 4 + i) + (row + 1));
            practices[i] = getCellIntValue(Character.toString(col + 4 + i) + (row + 2));
            individual[i] = getCellIntValue(Character.toString(col + 4 + i) + (row + 3));
            sum += lectures[i] + labs[i] + practices[i] + individual[i];
        }
        // Ещё одна проверка на незаполненные дисциплины (по отсутствию часов в неделю)
        if(sum == 0){
            return null;
        }
        subject.setLectures(lectures);
        subject.setLabs(labs);
        subject.setPractices(practices);
        subject.setIndividual(individual);
        subject.setPass(getCellIntValue(Character.toString(col + 23) + row));
        subject.setExam(getCellIntValue(Character.toString(col + 25) + row));
        subject.setCourseProject(getCellIntValue(Character.toString(col + 23) + (row + 1)));
        subject.setCourseWork(getCellIntValue(Character.toString(col + 25) + (row + 1)));
        subject.setK(getCellIntValue(Character.toString(col + 23) + (row + 2)));
        subject.setGradedPass(getCellIntValue(Character.toString(col + 25) + (row + 3)));
        return subject;
    }

    public static void main(String[] args) {
        try {
            PlanParser parser = new PlanParser();
            Plan plan = parser.parse("D:/2_5379811095064088210.xlsx");
            System.out.println(plan);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

public class tester {

    public static void getPDF() throws Exception {
        Workbook workbook = new Workbook("tester\\src\\workbook.xlsx");
        workbook.save("AsposeConvert.pdf", SaveFormat.PDF);
    }

    public static void main(String[] args) throws Exception {
        getPDF();
    }
}

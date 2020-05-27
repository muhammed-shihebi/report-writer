package sample.handlers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import sample.model.InspectionResult;
import sample.model.Report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFHandler {

    public static final int MAXSTRINGSIZE = 50;

    // ======= MyCell ==========================

    public static class MyCell{
        private String data;
        private int x;
        private int y;

        public MyCell(String data, int x, int y) {
            this.data = data;
            this.x = x;
            this.y = y;
        }

        public String getData() {
            return data;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static ArrayList<PDFHandler.MyCell> getMyCells(Report report){
        ArrayList<PDFHandler.MyCell> myCells = new ArrayList<>();

        // First row y = 710 next =  y - 18

        int x = 150;
        int y = 710;

        myCells.add(new PDFHandler.MyCell(report.getCustomer().getName(), x, y));
        myCells.add(new PDFHandler.MyCell(report.getProjectName().toString(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getCustomer().getTestPlace(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getInspectionStandard(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getEvaluationStandard(), x, y-=18));

        x = 390;
        y = 710;

        myCells.add(new PDFHandler.MyCell(report.getInspectionProcedure(), x, y));
        myCells.add(new PDFHandler.MyCell(((Integer)report.getInspectionScope()).toString() + "%", x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getDrawingNo(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getSurfaceCondition().toString(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getStageOfExamination().toString(), x, y-=18));

        x = 510;
        y = 710;

        myCells.add(new PDFHandler.MyCell(((Integer) report.getNumberOfPages()).toString(), x, y));
        myCells.add(new PDFHandler.MyCell(report.getReportNo(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getReportDate().toString(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getJobOrderNo().toString(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getOfferNo().toString(), x, y-=18));

        // Second row y = 608 next = y - 18

        x = 150;
        y = 608;

        myCells.add(new PDFHandler.MyCell(((Double) report.getEquipment().getPoleDistance()).toString() + "mm", x, y));
        myCells.add(new PDFHandler.MyCell(report.getEquipment().getEquipment(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getEquipment().getMPCarrierMedium(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getEquipment().getMagTech(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getEquipment().getUVLightIntensity(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getEquipment().getDistanceOfLight(), x, y-=18));

        x = 320;
        y = 608;

        myCells.add(new PDFHandler.MyCell(report.getExaminationArea(), x, y));
        myCells.add(new PDFHandler.MyCell(report.getCurrentType(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getLuxmeter(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getTestMedium(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getDemagnetization(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getHeatTreatment(), x, y-=18));

        x = 495;
        y = 608;

        myCells.add(new PDFHandler.MyCell(((Double)report.getSurfaceTemperature()).toString() + "ºc", x,y));
        myCells.add(new PDFHandler.MyCell(report.getGaussFieldStrength()+" kA/m", x, y-=27));
        myCells.add(new PDFHandler.MyCell(report.getEquipmentSurfaceCondition(), x-3, y-=27));
        myCells.add(new PDFHandler.MyCell(report.getIdentificationOfLightEquip(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getLiftingTestDateNumber(), x, y-=18));

        // Third row y = 437 next = y-18
        x = 190;
        y = 437;

        myCells.add(new PDFHandler.MyCell(report.getStandardDeviations(), x, y));
        myCells.add(new PDFHandler.MyCell(report.getInspectionDates().toString(), x, y-=18));
        myCells.add(new PDFHandler.MyCell(report.getDescriptionAndAttachments(), x, y-=18));

        // Forth row y = 355 next = y-14
        y = 355;

        for(int i = 0; i < report.getInspectionResults().size(); i++){
            InspectionResult inspectionResult = report.getInspectionResults().get(i);
            myCells.add(new PDFHandler.MyCell(inspectionResult.getWeldPieceNo(), 110, y));
            myCells.add(new PDFHandler.MyCell(((Double)inspectionResult.getTestLength()).toString(), 200, y));
            myCells.add(new PDFHandler.MyCell(inspectionResult.getWeldingProcess(), 260, y));
            myCells.add(new PDFHandler.MyCell(((Double)inspectionResult.getThickness()).toString(), 335, y));
            myCells.add(new PDFHandler.MyCell(inspectionResult.getDiameter(), 375, y));
            myCells.add(new PDFHandler.MyCell(inspectionResult.getDefectType(), 425, y));
            myCells.add(new PDFHandler.MyCell(inspectionResult.getDefectLoc(), 472,  y));
            myCells.add(new PDFHandler.MyCell(inspectionResult.getResult(), 540, y));
            y -= 14;
        }

        // Fifth row y = 143 next = y - 17
        x = 170;
        y = 143;

        myCells.add(new PDFHandler.MyCell(report.getOperator().toString(),x, y));
        myCells.add(new PDFHandler.MyCell("Level " +((Integer)report.getOperator().getLevel()).toString(), x, y-=17));
        myCells.add(new PDFHandler.MyCell(report.getOperatorDate().toString(), x, y-=17));

        x = 285;
        y = 143;

        myCells.add(new PDFHandler.MyCell(report.getEvaluator().toString(),x, y));
        myCells.add(new PDFHandler.MyCell("Level " + ((Integer)report.getEvaluator().getLevel()).toString(), x, y-=17));
        myCells.add(new PDFHandler.MyCell(report.getEvaluatorDate().toString(), x, y-=17));

        x = 407;
        y = 143;

        myCells.add(new PDFHandler.MyCell(report.getConformer().toString(),x,y));
        myCells.add(new PDFHandler.MyCell("Level " +((Integer)report.getConformer().getLevel()).toString(), x, y-=17));
        myCells.add(new PDFHandler.MyCell(report.getConformerDate().toString(), x, y-=17));

        return myCells;
    }

    // ======= PDF Functions ====================

    public static boolean isStringLegal(String str){
        if(str.length() > MAXSTRINGSIZE)
            return false;
        for(int i = 0; i < str.length(); i++){
            if (!WinAnsiEncoding.INSTANCE.contains(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void getPDF(Report report, String path) throws IOException {
        File file = new File("src/assets/reports/report.pdf");
        System.out.println("File is opened");
        PDDocument document = PDDocument.load(file);
        PDPage page = document.getPage(0);

        ArrayList<PDFHandler.MyCell> myCells = getMyCells(report);
        for (PDFHandler.MyCell myCell : myCells) {
            writeCell(myCell, document, page);
        }

        if(report.isFilerWeld()){
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            PDImageXObject pdImage = PDImageXObject.createFromFile("src/assets/images/yesIcon.png", document);
            contentStream.drawImage(pdImage, 302, 449);
            contentStream.close();
        }
        if(report.isButtWeld()){
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            PDImageXObject pdImage = PDImageXObject.createFromFile("src/assets/images/yesIcon.png", document);
            contentStream.drawImage(pdImage, 173, 449);
            contentStream.close();
        }

        document.save(path);
        System.out.println("File is saved");
        document.close();
    }

    public static void writeCell(MyCell myCell, PDDocument document, PDPage page) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.beginText();
        contentStream.newLineAtOffset(myCell.getX(), myCell.getY());
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 6);
        contentStream.showText(myCell.getData());
        contentStream.endText();
        contentStream.close();
    }
}

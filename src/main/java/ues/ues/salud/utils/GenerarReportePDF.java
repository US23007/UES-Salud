/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import ues.ues.salud.model.DetalleReceta;
import ues.ues.salud.model.Paciente;

/**
 *
 * @author su487
 */
public class GenerarReportePDF {
    public static void generarRecetaPDF(Paciente pac,String doctor,String especialidad,String diagnostico,List<DetalleReceta> medicamentos){
        String nombreArchivo = "Receta_" + pac.getCarnet() + ".pdf";
        Document doc = new Document();
        
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(nombreArchivo));
            doc.open();
            
            // Fuentes estilizadas
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fuenteSub = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            // =================================================================
            // 🌟 ENCABEZADO CON LOGO DE LA MINERVA (Tabla de 2 columnas)
            // =================================================================
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setWidths(new float[]{15f, 85f}); // 15% para el logo, 85% para el texto
            
            // Celda 1: El Logo de la Minerva
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER); // Hacemos la celda invisible
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            try {
                // Ruta de tu imagen en el proyecto
                String rutaLogo = "src/main/resources/img/minerva.png"; 
                Image imgLogo = Image.getInstance(rutaLogo);
                
                // Escalamos la imagen para que quepa perfectamente en el encabezado
                imgLogo.scaleToFit(60, 60); 
                imgLogo.setAlignment(Element.ALIGN_CENTER);
                celdaLogo.addElement(imgLogo);
            } catch (Exception e) {
                System.out.println("No se pudo cargar el logo de la Minerva, se omitirá: " + e.getMessage());
                // Si la imagen no se encuentra, dejamos la celda en blanco para que no se caiga el programa
            }
            tablaEncabezado.addCell(celdaLogo);
            
            // Celda 2: Texto Institucional
            PdfPCell celdaTexto = new PdfPCell();
            celdaTexto.setBorder(PdfPCell.NO_BORDER); // Invisible
            celdaTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Paragraph titulo = new Paragraph("UNIVERSIDAD DE EL SALVADOR\nCLÍNICA UES-SALUD", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(titulo);
            
            Paragraph sub = new Paragraph("COMPROBANTE DE ATENCIÓN Y RECETA MÉDICA", fuenteSub);
            sub.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(sub);
            
            tablaEncabezado.addCell(celdaTexto);
            
            // Añadimos el encabezado completo al documento
            doc.add(tablaEncabezado);
            
            // Espacio de separación antes del cuerpo
            doc.add(new Paragraph("\n")); 
            // =================================================================
            
            // Datos del Paciente y la Consulta (Lo que ya tenías armado)
            doc.add(new Paragraph("Carnet Paciente: " + pac.getCarnet(), fuenteNormal));
            doc.add(new Paragraph("Nombre Completo: " + pac.getNombre_paciente() + " " + pac.getApellido_paciente(), fuenteNormal));
            doc.add(new Paragraph("Atendido Por:    " + doctor + " (" + especialidad + ")", fuenteNormal));
            doc.add(new Paragraph("\nDIAGNÓSTICO MÉDICO:\n" + diagnostico + "\n\n", fuenteNormal));
            
            // Título de la tabla de medicamentos
            doc.add(new Paragraph("MEDICAMENTOS RECETADOS:", fuenteBold));
            doc.add(new Paragraph(" ")); 
            
            // Estructura de la Tabla de Medicamentos
            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{30f, 25f, 45f});
            
            String[] encabezados = {"Medicamento", "Dosis", "Indicaciones"};
            for (String enc : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(enc, fuenteBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(6f);
                tabla.addCell(cell);
            }
            
            for (DetalleReceta med : medicamentos) {
                tabla.addCell(new Phrase(med.getNombre_medicamento(), fuenteNormal));
                tabla.addCell(new Phrase(med.getDosis(), fuenteNormal));
                tabla.addCell(new Phrase(med.getIndicaciones(), fuenteNormal));
            }
            
            doc.add(tabla);
            
            // Mensaje de cierre
            Paragraph pie = new Paragraph("\n\n==================================================\n¡Recupérese pronto! Presentar este documento en Farmacia.", fuenteNormal);
            pie.setAlignment(Element.ALIGN_CENTER);
            doc.add(pie);
            
            // Abrir automáticamente el archivo
            File archivoPdf = new File(nombreArchivo);
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivoPdf);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (doc.isOpen()) {
                doc.close();
            }
        }
    
            
        
        
    }
}


package ues.ues.salud.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import ues.ues.salud.model.DetalleReceta;
import ues.ues.salud.model.Paciente;

/**
 *
 * Clase GenerarReportePDF : Como su nombre lo indica esta clase es el encargado de tomar la informacion de los Dao y escribirlos en pdf para la generacion de reportes , Se ha utilizado la libreria de  itextpdf y jfree.chart para 
 * la generación de graficos y estructurar el pdf
 *  Fecha : 06/06/2026
 * @author US23007 Samuel De Jesus Umaña Sorto
 */
public class GenerarReportePDF {
    //Funcion para generar la receta, consulta , ticket de la consulta realizada donde recibe un objeto del tipo Persona y otros atributos importantes
    public static void generarRecetaPDF(Paciente pac,String doctor,String especialidad,String diagnostico,List<DetalleReceta> medicamentos){
        String rutaCarpeta = "C:/UES-SALUD/consultas/"; //Asignamos el archivo de salida en la ruta creada anteriormente en la clase InicializarSistema
        String nombreArchivo = "Receta_" + pac.getCarnet() + ".pdf";
        
        File directorio = new File(rutaCarpeta);
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

       
        File archivoPdf = new File(directorio, nombreArchivo);
        Document doc = new Document();
        
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(archivoPdf));
            doc.open(); //Abrimos el documento pfd recien creado
            
            //Estilos y Tipos de Fuentes
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fuenteSub = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            //Tabla 
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setWidths(new float[]{15f, 85f}); 
            
            //Celdas
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER); // Hacemos la celda invisible
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            try {
                //Obtenemos la ruta del logo de la ues para más profesionalismo
                java.net.URL urlLogo = GenerarReportePDF.class.getResource("/img/minerva.png");
                if (urlLogo != null) {
                    Image imgLogo = Image.getInstance(urlLogo);
                    imgLogo.scaleToFit(60, 60);
                    imgLogo.setAlignment(Element.ALIGN_CENTER);
                    celdaLogo.addElement(imgLogo);
                } else {
                    System.out.println("No se encontró la imagen en los recursos internos.");
                }
            } catch (Exception e) {
                System.out.println("No se pudo cargar el logo de la Minerva, se omitirá: " + e.getMessage());
                
            }
            tablaEncabezado.addCell(celdaLogo); //Asignamos la imagen a la celda
            
            //Texto Institucional
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
            
            // Datos del Paciente y la Consulta 
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
            
            
            Paragraph pie = new Paragraph("\n\n==================================================\n¡Recupérese pronto! Presentar este documento en Farmacia.", fuenteNormal);
            pie.setAlignment(Element.ALIGN_CENTER);
            doc.add(pie);
            
            doc.close();
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
    
    //Metodo para Generar el reporte de Atenciones entre un rango de fechas especificadas
    public static void generarReporteAtenciones(LocalDate inicio, LocalDate fin, List<String[]> atenciones) {
        String rutaCarpeta = "C:/UES-SALUD/reportes/";
        DateTimeFormatter formatoFichero = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nombreArchivo = "Reporte_Atenciones_" + inicio.format(formatoFichero) + "_al_" + fin.format(formatoFichero) + ".pdf";
        
        File directorio = new File(rutaCarpeta);
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivoPdf = new File(directorio, nombreArchivo);
        
        Document doc = new Document(PageSize.A4.rotate());
        
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(archivoPdf));
            doc.open();
            
            
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fuenteSub = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setWidths(new float[]{10f, 90f}); 
            
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER);
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            try {
                 java.net.URL urlLogo = GenerarReportePDF.class.getResource("/img/minerva.png");
                if (urlLogo != null) {
                    Image imgLogo = Image.getInstance(urlLogo);
                    imgLogo.scaleToFit(60, 60);
                    imgLogo.setAlignment(Element.ALIGN_CENTER);
                    celdaLogo.addElement(imgLogo);
                } else {
                    System.out.println("No se encontró la imagen en los recursos internos.");
                }
            } catch (Exception e) {
                System.out.println("No se pudo cargar el logo en reporte general: " + e.getMessage());
            }
            tablaEncabezado.addCell(celdaLogo);
            
            PdfPCell celdaTexto = new PdfPCell();
            celdaTexto.setBorder(PdfPCell.NO_BORDER);
            celdaTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Paragraph titulo = new Paragraph("UNIVERSIDAD DE EL SALVADOR\nCLÍNICA MÉDICA UES-SALUD", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(titulo);
            
            DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Paragraph sub = new Paragraph("REPORTE HISTORIAL DE ATENCIONES GENERALES\nPeríodo: " 
                    + inicio.format(formatoEspanol) + " al " + fin.format(formatoEspanol), fuenteSub);
            sub.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(sub);
            
            tablaEncabezado.addCell(celdaTexto);
            doc.add(tablaEncabezado);
            doc.add(new Paragraph("\n")); 
            
            
            if (atenciones.isEmpty()) {
                Paragraph vacio = new Paragraph("No se encontraron registros de pacientes atendidos en el rango de fechas seleccionado.", fuenteBold);
                vacio.setAlignment(Element.ALIGN_CENTER);
                doc.add(vacio);
            } else {
                
                PdfPTable tablaDatos = new PdfPTable(5);
                tablaDatos.setWidthPercentage(100);
                tablaDatos.setWidths(new float[]{15f, 15f, 35f, 20f, 15f}); 
                
                String[] encabezados = {"Fecha Consulta", "Carnet Alumno", "Nombre del Paciente", "Especialidad", "Triaje / Urgencia"};
                
                
                for (String enc : encabezados) {
                    PdfPCell cell = new PdfPCell(new Phrase(enc, fuenteBold));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(6f);
                    tablaDatos.addCell(cell);
                }
                
                
                for (String[] fila : atenciones) {
                    tablaDatos.addCell(new Phrase(fila[0], fuenteNormal)); // Fecha
                    tablaDatos.addCell(new Phrase(fila[1], fuenteNormal)); // Carnet
                    tablaDatos.addCell(new Phrase(fila[2], fuenteNormal)); // Paciente
                    tablaDatos.addCell(new Phrase(fila[3], fuenteNormal)); // Especialidad
                    
                    
                    PdfPCell celdaUrgencia = new PdfPCell(new Phrase(fila[4], fuenteNormal));
                    celdaUrgencia.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaDatos.addCell(celdaUrgencia);
                }
                
                doc.add(tablaDatos);
                
                
                Paragraph total = new Paragraph("\nTotal de pacientes atendidos en el período: " + atenciones.size(), fuenteBold);
                total.setAlignment(Element.ALIGN_RIGHT);
                doc.add(total);
            }
            
            
            doc.close();
            //Abrir automaticamente 
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivoPdf);
            }
            
        } catch (Exception e) {
            Notifications.create()
                        .title("Error Inesperado")
                        .text("No se regenero el Reporte de Atenciones")
                        .hideAfter(Duration.seconds(3)) 
                        .position(Pos.BOTTOM_RIGHT) 
                        .showError();
            
            e.printStackTrace();
        } finally {
            if (doc.isOpen()) {
                doc.close();
            }
        }
    }
    
    
    //Método para generar el Expediente Clinico de un paciente en Concreto
    public static void generarExpedienteClinico(Paciente pac, List<String[]> historial) {
        String rutaCarpeta = "C:/UES-SALUD/reportes/";
        String nombreArchivo = "Expediente_" + pac.getCarnet() + ".pdf";
        
        File directorio = new File(rutaCarpeta);
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivoPdf = new File(directorio, nombreArchivo);
        Document doc = new Document(PageSize.A4); // Vertical estándar
        
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(archivoPdf));
            doc.open();
            
            // Fuentes estilizadas
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fuenteSub = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            // =================================================================
            // ENCABEZADO CON LOGO DE LA UES
            // =================================================================
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setWidths(new float[]{15f, 85f}); 
            
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER); 
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            try {
                 java.net.URL urlLogo = GenerarReportePDF.class.getResource("/img/minerva.png");
                if (urlLogo != null) {
                    Image imgLogo = Image.getInstance(urlLogo);
                    imgLogo.scaleToFit(60, 60);
                    imgLogo.setAlignment(Element.ALIGN_CENTER);
                    celdaLogo.addElement(imgLogo);
                } else {
                    System.out.println("No se encontró la imagen en los recursos internos.");
                }
            } catch (Exception e) {
                System.out.println("No se pudo cargar el logo de la Minerva: " + e.getMessage());
            }
            tablaEncabezado.addCell(celdaLogo);
            
            PdfPCell celdaTexto = new PdfPCell();
            celdaTexto.setBorder(PdfPCell.NO_BORDER); 
            celdaTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Paragraph titulo = new Paragraph("UNIVERSIDAD DE EL SALVADOR\nCLÍNICA UES-SALUD", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(titulo);
            
            Paragraph sub = new Paragraph("EXPEDIENTE CLÍNICO INTEGRAL DEL ESTUDIANTE", fuenteSub);
            sub.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(sub);
            
            tablaEncabezado.addCell(celdaTexto);
            doc.add(tablaEncabezado);
            doc.add(new Paragraph("\n")); 
            
            // =================================================================
            // DATOS GENERALES DEL PACIENTE (Bloque de afiliación)
            // =================================================================
            Paragraph seccionDatos = new Paragraph("DATOS GENERALES DEL AFILIADO\n", fuenteBold);
            doc.add(seccionDatos);
            
            doc.add(new Paragraph("Carnet Universitario: " + pac.getCarnet(), fuenteNormal));
            doc.add(new Paragraph("Nombre del Paciente:  " + pac.getNombre_paciente() + " " + pac.getApellido_paciente(), fuenteNormal));
            // Si tu modelo maneja sexo, carrera o edad los puedes añadir aquí perfectamente
            doc.add(new Paragraph("\n---------------------------------------------------------------------------------------------------------------------------------"));
            doc.add(new Paragraph("\nHISTORIAL CRONOLÓGICO DE ATENCIONES:\n\n", fuenteBold));
            
            // =================================================================
            // TABLA DE VISITAS MÉDICAS
            // =================================================================
            if (historial.isEmpty()) {
                Paragraph sinRegistros = new Paragraph("Este estudiante no registra ninguna atención médica previa en el sistema.", fuenteNormal);
                doc.add(sinRegistros);
            } else {
                PdfPTable tablaHistorial = new PdfPTable(4);
                tablaHistorial.setWidthPercentage(100);
                tablaHistorial.setWidths(new float[]{20f, 25f, 40f, 15f}); // Distribución de ancho
                
                String[] encabezados = {"Fecha de Registro", "Especialidad Atendida", "Síntomas Reportados", "Urgencia"};
                for (String enc : encabezados) {
                    PdfPCell cell = new PdfPCell(new Phrase(enc, fuenteBold));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(5f);
                    tablaHistorial.addCell(cell);
                }
                
                for (String[] consulta : historial) {
                    tablaHistorial.addCell(new Phrase(consulta[0], fuenteNormal)); // Fecha
                    tablaHistorial.addCell(new Phrase(consulta[1], fuenteNormal)); // Especialidad
                    tablaHistorial.addCell(new Phrase(consulta[2], fuenteNormal)); // Síntomas
                    
                    PdfPCell celdaUrg = new PdfPCell(new Phrase(consulta[3], fuenteNormal));
                    celdaUrg.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaHistorial.addCell(celdaUrg); // Nivel Urgencia
                }
                
                doc.add(tablaHistorial);
            }
            
            // Cierre e impresión automática
            doc.close();
            //Abrir automaticamente 
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
    
    public static void generarReporteSintomas(LocalDate inicio, LocalDate fin, List<String[]> datos) {
        String rutaCarpeta = "C:/UES-SALUD/reportes/";
        DateTimeFormatter formatoFichero = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nombreArchivo = "Reporte_Sintomas_" + inicio.format(formatoFichero) + "_al_" + fin.format(formatoFichero) + ".pdf";
        
        File directorio = new File(rutaCarpeta);
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivoPdf = new File(directorio, nombreArchivo);
        Document doc = new Document(PageSize.A4);
        
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(archivoPdf));
            doc.open();
            
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fuenteSub = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font fuenteBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
            
            // =================================================================
            // ENCABEZADO CON LOGO DE LA UES
            // =================================================================
            PdfPTable tablaEncabezado = new PdfPTable(2);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setWidths(new float[]{15f, 85f}); 
            
            PdfPCell celdaLogo = new PdfPCell();
            celdaLogo.setBorder(PdfPCell.NO_BORDER); 
            celdaLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            try {
                 java.net.URL urlLogo = GenerarReportePDF.class.getResource("/img/minerva.png");
                if (urlLogo != null) {
                    Image imgLogo = Image.getInstance(urlLogo);
                    imgLogo.scaleToFit(60, 60);
                    imgLogo.setAlignment(Element.ALIGN_CENTER);
                    celdaLogo.addElement(imgLogo);
                } else {
                    System.out.println("No se encontró la imagen en los recursos internos.");
                }
            } catch (Exception e) {
                System.out.println("No se pudo cargar el logo: " + e.getMessage());
            }
            tablaEncabezado.addCell(celdaLogo);
            
            PdfPCell celdaTexto = new PdfPCell();
            celdaTexto.setBorder(PdfPCell.NO_BORDER); 
            celdaTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            Paragraph titulo = new Paragraph("UNIVERSIDAD DE EL SALVADOR\nCLÍNICA UES-SALUD", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(titulo);
            
            DateTimeFormatter formatoEspanol = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Paragraph sub = new Paragraph("BOLETÍN DE ALERTA EPIDEMIOLÓGICA Y SÍNTOMAS COMUNES\nPeríodo: " 
                    + inicio.format(formatoEspanol) + " al " + fin.format(formatoEspanol), fuenteSub);
            sub.setAlignment(Element.ALIGN_CENTER);
            celdaTexto.addElement(sub);
            
            tablaEncabezado.addCell(celdaTexto);
            doc.add(tablaEncabezado);
            doc.add(new Paragraph("\n")); 
            
            // =================================================================
            // TABLA DE DATOS
            // =================================================================
            if (datos.isEmpty()) {
                Paragraph vacio = new Paragraph("No se registran datos de síntomas en este rango.", fuenteBold);
                vacio.setAlignment(Element.ALIGN_CENTER);
                doc.add(vacio);
            } else {
                PdfPTable tablaSintomas = new PdfPTable(3);
                tablaSintomas.setWidthPercentage(90);
                tablaSintomas.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaSintomas.setWidths(new float[]{15f, 60f, 25f});
                
                String[] encabezados = {"Posición", "Sintomatología / Malestar", "Frecuencia (Casos)"};
                for (String enc : encabezados) {
                    PdfPCell cell = new PdfPCell(new Phrase(enc, fuenteBold));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    cell.setPadding(5f);
                    tablaSintomas.addCell(cell);
                }
                
                int contador = 1;
                for (String[] fila : datos) {
                    PdfPCell celdaPos = new PdfPCell(new Phrase("#" + contador, fuenteNormal));
                    celdaPos.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaSintomas.addCell(celdaPos);
                    
                    tablaSintomas.addCell(new Phrase(fila[0], fuenteNormal));
                    
                    PdfPCell celdaCant = new PdfPCell(new Phrase(fila[1], fuenteNormal));
                    celdaCant.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaSintomas.addCell(celdaCant);
                    
                    contador++;
                }
                doc.add(tablaSintomas);
                doc.add(new Paragraph("\n\n")); // Espacio para el gráfico
                
                // =================================================================
                // Generacion de ChartBart
                // =================================================================
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                
                // Llenamos el dataset con la info de la BD (mostramos máximo los top 5 o 7 para que no se amontone)
                int limiteGrafico = Math.min(datos.size(), 5); 
                for (int i = 0; i < limiteGrafico; i++) {
                    String[] fila = datos.get(i);
                    String sintoma = fila[0];
                    int cantidad = Integer.parseInt(fila[1]);
                    
                    // Si el nombre del síntoma es larguísimo, lo recortamos un poco para el eje X
                    if(sintoma.length() > 15) {
                        sintoma = sintoma.substring(0, 12) + "...";
                    }
                    
                    dataset.addValue(cantidad, "Casos", sintoma);
                }
                
                // Creamos el gráfico de barras en 3D
                JFreeChart graficoBarras = ChartFactory.createBarChart(
                        "Top Síntomas Más Frecuentes",     
                        "Síntomas Detectados",             
                        "Número de Pacientes",            
                        dataset,                          
                        PlotOrientation.VERTICAL,          
                        false,                             
                        true,                             
                        false                              
                );
                
                // Convertimos el gráfico a bytes de imagen en formato PNG (Ancho: 520, Alto: 280)
                byte[] bytesGrafico = ChartUtils.encodeAsPNG(graficoBarras.createBufferedImage(520, 280));
                
                // Creamos la instancia de imagen de iText
                Image imgGrafico = Image.getInstance(bytesGrafico);
                imgGrafico.setAlignment(Element.ALIGN_CENTER);
                
                // Añadimos el gráfico directamente al PDF
                doc.add(imgGrafico);
            }
            
            doc.close();
            //Abrir automaticamente 
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

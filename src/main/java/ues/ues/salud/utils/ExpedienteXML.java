/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ues.ues.salud.utils;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ues.ues.salud.model.Paciente;

/**
 *Clase ExpedienteXML : está clase será implementada para darle dinamismo a nuestro sistema ya que podremos cargar los expedientes de los estudiantes para
 * su actualización en caso de una nueva visita al sistema de Salud UES-Salud
 * @author Samuel De Jesús Umaña Sorto US23007
 * Fecha: 12/06/2026
 */
public class ExpedienteXML {
    
    public static void generarExpedienteXML(Paciente paciente , String Especialidad,String nivelUrgencia) throws ParserConfigurationException, TransformerConfigurationException, TransformerException{
        DocumentBuilderFactory factor =  DocumentBuilderFactory.newInstance();
        DocumentBuilder  builder = factor.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        Element raiz = doc.createElement("expediente");
        doc.appendChild(raiz);
        
        Element nodoPaciente = doc.createElement("paciente");
        nodoPaciente.setAttribute("carnet", paciente.getCarnet());
        
        Element nodeNombre = doc.createElement("nombres");
        nodeNombre.setAttribute("nombres", paciente.getNombre_paciente());
        nodoPaciente.appendChild(nodeNombre);
        
        Element nodeApellido = doc.createElement("apellidos");
        nodeApellido.setAttribute("apellidos", paciente.getApellido_paciente());
        nodoPaciente.appendChild(nodeApellido);
        
        Element nodeFechaNacimiento = doc.createElement("fechaNacimiento");
        nodeFechaNacimiento.setAttribute("fechaNacimiento",paciente.getFecha_nacimiento().toString());
        nodoPaciente.appendChild(nodeFechaNacimiento);
        
        LocalDate fechaNacimiento = paciente.getFecha_nacimiento();
        
        Period periodo = Period.between(fechaNacimiento,LocalDate.now());
        
        int edad = periodo.getYears();
        Element nodeEdad = doc.createElement("edad");
        nodeEdad.setAttribute("edad",String.valueOf(edad));
        nodoPaciente.appendChild(nodeEdad);
        
        Element sexo = doc.createElement("sexo");
        sexo.setAttribute("sexo",paciente.getSexo());
        nodoPaciente.appendChild(sexo);
                
        raiz.appendChild(nodoPaciente);
        
        
        Element nodoEmergencia = doc.createElement("emergencia");
        Element esp = doc.createElement("especialidad");
        esp.appendChild(doc.createTextNode(Especialidad));
        nodoEmergencia.appendChild(esp);
        
        Element sint = doc.createElement("sintomas");
        sint.appendChild(doc.createTextNode(paciente.getSintomas()));
        nodoEmergencia.appendChild(sint);
        
        Element urgencia = doc.createElement("nivel_urgencia");
        urgencia.appendChild(doc.createTextNode(nivelUrgencia));
        nodoEmergencia.appendChild(urgencia);
        
        Element fechaAtención = doc.createElement("fecha_atencion");
        fechaAtención.appendChild(doc.createTextNode(java.time.LocalDateTime.now().toString()));
        nodoEmergencia.appendChild(fechaAtención);
        
        raiz.appendChild(nodoEmergencia);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transfomer = transformerFactory.newTransformer();
        transfomer.setOutputProperty(OutputKeys.INDENT ,"yes");
        transfomer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        
        String ruta = "C:/UES-SALUD/expedientes";
        File carpetaExpediente = new File(ruta);
        
        if(!carpetaExpediente.exists()){
            carpetaExpediente.mkdir();
        }
        DOMSource source = new DOMSource(doc);
        
        String nombreExpediente = paciente.getCarnet()+".xml";
        
        File ExpedienteFinal = new File(carpetaExpediente,nombreExpediente);
        StreamResult result = new StreamResult(ExpedienteFinal);
        
        transfomer.transform(source, result);
        
        System.out.println("Generado Correctamente = " + ExpedienteFinal.getAbsolutePath());
        
    }
}

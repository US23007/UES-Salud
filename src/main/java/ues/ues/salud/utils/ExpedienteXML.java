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
import ues.ues.salud.model.Especialidad;
import ues.ues.salud.model.Paciente;
import ues.ues.salud.model.Triaje;

/**
 *Clase ExpedienteXML : está clase será implementada para darle dinamismo a nuestro sistema ya que podremos cargar los expedientes de los estudiantes para
 * su actualización en caso de una nueva visita al sistema de Salud UES-Salud
 * @author Samuel De Jesús Umaña Sorto US23007
 * Fecha: 12/06/2026
 */
public class ExpedienteXML {
    
    public static void generarExpedienteXML(Paciente paciente, String sintomas,Double temperatura,String presion,String Especialidad, String nivelUrgencia) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory factor = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factor.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Elemento Raíz
        Element raiz = doc.createElement("expediente");
        doc.appendChild(raiz);

        // Nodo Paciente con Atributo Identificador Único
        Element nodoPaciente = doc.createElement("paciente");
        nodoPaciente.setAttribute("carnet", paciente.getCarnet());

        // Nombres (Como texto interno)
        Element nodeNombre = doc.createElement("nombres");
        nodeNombre.appendChild(doc.createTextNode(paciente.getNombre_paciente()));
        nodoPaciente.appendChild(nodeNombre);

        // Apellidos
        Element nodeApellido = doc.createElement("apellidos");
        nodeApellido.appendChild(doc.createTextNode(paciente.getApellido_paciente()));
        nodoPaciente.appendChild(nodeApellido);

        // Fecha de Nacimiento
        Element nodeFechaNacimiento = doc.createElement("fechaNacimiento");
        nodeFechaNacimiento.appendChild(doc.createTextNode(paciente.getFecha_nacimiento().toLocalDate().toString()));
        nodoPaciente.appendChild(nodeFechaNacimiento);

        // Cálculo de Edad
        LocalDateTime fechaNacimiento = paciente.getFecha_nacimiento();
        Period periodo = Period.between(fechaNacimiento.toLocalDate(), LocalDate.now());
        int edad = periodo.getYears();

        Element nodeEdad = doc.createElement("edad");
        nodeEdad.appendChild(doc.createTextNode(String.valueOf(edad)));
        nodoPaciente.appendChild(nodeEdad);

        // Sexo
        Element nodeSexo = doc.createElement("sexo");
        nodeSexo.appendChild(doc.createTextNode(paciente.getSexo()));
        nodoPaciente.appendChild(nodeSexo);

        // Teléfono (CORREGIDO: usando su propia variable)
        Element nodeTelefono = doc.createElement("telefono");
        nodeTelefono.appendChild(doc.createTextNode(paciente.getTelefono()));
        nodoPaciente.appendChild(nodeTelefono);

        // Dirección (CORREGIDO: usando su propia variable)
        Element nodeDireccion = doc.createElement("direccion");
        nodeDireccion.appendChild(doc.createTextNode(paciente.getDireccion()));
        nodoPaciente.appendChild(nodeDireccion);

        raiz.appendChild(nodoPaciente);

        // Nodo Emergencia
        Element nodoEmergencia = doc.createElement("emergencia");

        Element esp = doc.createElement("especialidad");
        esp.appendChild(doc.createTextNode(Especialidad));
        nodoEmergencia.appendChild(esp);
        
        Element sint = doc.createElement("sintomas");
        sint.appendChild(doc.createTextNode(sintomas));
        nodoEmergencia.appendChild(sint);
        
        Element temp = doc.createElement("temperatura");
        temp.appendChild(doc.createTextNode(temperatura.toString()));
        nodoEmergencia.appendChild(temp);
        
        Element pre = doc.createElement("presion");
        pre.appendChild(doc.createTextNode(presion));
        nodoEmergencia.appendChild(pre);
        
        Element urgencia = doc.createElement("nivel_urgencia");
        urgencia.appendChild(doc.createTextNode(nivelUrgencia));
        nodoEmergencia.appendChild(urgencia);

        Element fechaAtencion = doc.createElement("fecha_atencion");
        fechaAtencion.appendChild(doc.createTextNode(java.time.LocalDateTime.now().toString()));
        nodoEmergencia.appendChild(fechaAtencion);

        raiz.appendChild(nodoEmergencia);

        // Transformación y Guardado Físico
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        String ruta = "C:/UES-SALUD/expedientes";
        File carpetaExpediente = new File(ruta);

        if (!carpetaExpediente.exists()) {
            carpetaExpediente.mkdirs(); // mkdirs() asegura crear carpetas intermedias si faltan
        }

        DOMSource source = new DOMSource(doc);
        String nombreExpediente = paciente.getCarnet() + ".xml";
        File expedienteFinal = new File(carpetaExpediente, nombreExpediente);
        StreamResult result = new StreamResult(expedienteFinal);

        transformer.transform(source, result);
        System.out.println("Generado Correctamente = " + expedienteFinal.getAbsolutePath());

    }
    
    public static Paciente cargarExpedienteXML(File archivoXML) throws Exception {
        // 1. Parsear el archivo seleccionado
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(archivoXML);
        doc.getDocumentElement().normalize();

        // 2. Extraer datos del Paciente
        Element pacienteNode = (Element) doc.getElementsByTagName("paciente").item(0);
        String carnet = pacienteNode.getAttribute("carnet");
        String nombres = pacienteNode.getElementsByTagName("nombres").item(0).getTextContent();
        String apellidos = pacienteNode.getElementsByTagName("apellidos").item(0).getTextContent();
        String fechaNacStr = pacienteNode.getElementsByTagName("fechaNacimiento").item(0).getTextContent();
        LocalDateTime fechaNacimiento = java.time.LocalDate.parse(fechaNacStr).atStartOfDay();

        String sexo = pacienteNode.getElementsByTagName("sexo").item(0).getTextContent();
        String telefono = pacienteNode.getElementsByTagName("telefono").item(0).getTextContent();
        String direccion = pacienteNode.getElementsByTagName("direccion").item(0).getTextContent();
        // 3. Extraer datos de la Emergencia
        Element emergenciaNode = (Element) doc.getElementsByTagName("emergencia").item(0);
        String sintomas = emergenciaNode.getElementsByTagName("sintomas").item(0).getTextContent();
        String nivelUrgencia = emergenciaNode.getElementsByTagName("nivel_urgencia").item(0).getTextContent();
        String temperatura = emergenciaNode.getElementsByTagName("temperatura").item(0).getTextContent();
        String especialidad = emergenciaNode.getElementsByTagName("especialidad").item(0).getTextContent();
        String presion = emergenciaNode.getElementsByTagName("presion").item(0).getTextContent();

        Especialidad espe = new Especialidad();
        espe.setNombreEspecialidad(especialidad);
        Triaje triaje = new Triaje();
        triaje.setSintomas(sintomas);
        triaje.setNivel_urgencia(nivelUrgencia);
        triaje.setPresionArterial(presion);
        triaje.setTemperatura(Double.parseDouble(temperatura));
        
        Paciente paciente = new Paciente(nombres, apellidos,carnet,sexo,telefono,fechaNacimiento,direccion);
        paciente.agregarTriaje(triaje);
        triaje.agregarEspecialidad(espe);
        espe.agregarTriaje(triaje);
        
        return paciente;
        
        
    }

}

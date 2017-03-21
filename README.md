
# Plataforma Banco Unión S.A

### Acerca de
El Banco Unión S.A provee un servicio seguro para poder consultar el detalle de los movimientos en una cuenta bancaria mediante SOAP.

Bajo esta funcionalidad, ADSIB y Banco Unión S.A trabajan con un servicios que  le permite a ADSIB ser cliente de consumo para obtener los siguientes datos de los últimos N movimientos realizados en su cuenta bancaria:

* Fecha
* Tipo de movimiento (Depósito o retiro)
* Numero del documento de identidad de la persona que realizo el movimiento.
* Monto del movimiento realizada, el valor es negativo si el movimiento es de un retiro
* Número del movimiento

### Componentes

Esta plataforma esta conformada por tres módulos:

* Representación del WSDL de consumo [busa-servicio](busa/busa-servicio)
* Cliente que se conecta con el Banco Unión [busa-cliente](busa/busa-cliente)
* Servidor que permite consultas REST  [busa-proxy](busa/busa-proxy)
* Firmador y verificador de los mensajes enviados [firmador-estatal]()


### Funcionalidades

La plataforma permite consultar los últimos  N movimientos realizados en una cuenta bancaria.

### Requisitos

* Acrónimo de la entidad consultante
* Número de cuenta de la entidad consultate
* Código Uninet de la entidad consultante
* IP de conexión
* Certificado digital y clave privada de la entidad consultante 
* Certficado digital de la Entidad donde se consulta 

Donde: 

* ADSIB es la entidad consultante
* Banco Unión es la entidad donde se consulta
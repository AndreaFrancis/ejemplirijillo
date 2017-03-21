Introducción
---------------------------------------------------------

Actualmente ADSIB presta servicios en el ámbito de las Tecnologías de la Información y la Comunicación como ser: Firma digital, Nic.bo, Repositorio Estatal y Sellado de tiempo (Proximamente).

Para el caso de Firma digital, Nic.bo y Sellado de tiempo, existen tarifas que los usuarios deben pagar para poder hacer uso de estos servicios, es en este punto donde los sistemas de ADSIB requieren realizar validaciones de pagos para efectuar su facturación.

Es de esta manera que se plantea la Plataforma BUSA para realizar estas operaciones.

Antecedentes
^^^^^^^^^^^^^^
El Banco Unión S.A provee un servicio seguro para poder consultar el detalle de los movimientos en una cuenta bancaria mediante SOAP.

Bajo esta funcionalidad, ADSIB y Banco Unión S.A firmaron un convenio bajo el cuál el banco le permite ser cliente de consumo para obtener los siguientes datos de los últimos N movimientos realizados en su cuenta bancaria:

* Fecha
* Tipo de movimiento (Depósito o retiro)
* Numero del documento de identidad de la persona que realizo el movimiento.
* Monto del movimiento realizada, el valor es negativo si el movimiento es de un retiro.
* Número del movimiento.

Objetivo
^^^^^^^^^
Implementar una plataforma de consulta de detalle de los movimientos realizados en una cuenta bancaria para los sistemas de ADSIB.

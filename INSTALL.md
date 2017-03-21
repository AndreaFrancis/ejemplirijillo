# Plataforma Banco Unión S.A

Para el funcionamiento de la plataforma, se realizarán tres etapas:

1. Instalación de requisitos
2. Instalación de dependencias
3. Instalación de la plataforma

## 1. Instalación de requisitos

*  Java 7
*  Maven
*  Túnel VPN
*  Git

### a) Instalación de OpenJDK 7

Actualizar el listado de repositorios
```sh
apt-get update
```

Instalar java 7
```sh
apt-get install openjdk-7-jdk openjdk-7-jre
```

Seleccionar java 7 para su uso
```sh
update-alternatives --config java
```
### b) Instalación de Maven

Instalar maven
```sh
sudo apt-get install maven2
```

### c) Instalación y configuración del túnel VPN

Instalar el cliente
```sh
apt-get install pptp-linux
```

Editar el siguiente archivo:
```sh
vi /etc/ppp/chap-secrets
```

Adicionar la siguiente línea:
```sh
166.114.1.49\\agetic PPTP 123456 *
```

Crear el siguiente archivo:
```sh
vi /etc/ppp/peers/banco-union-01
```

Copiar las siguientes líneas:
```sh
pty "pptp 166.114.1.49 --nolaunchpppd"
name 166.114.1.49\\agetic
remotename PPTP
 #require-mppe-128
file /etc/ppp/options.pptp
ipparam 166.114.1.49
```

Editar el siguiente archivo:

```sh
vi /etc/ppp/options.pptp
```

Comentar la siguiente línea, guardar y salir
```sh
#refuse-pap
```

Lanzar la VPN
```sh
pon banco-union-01
```

Ejecutar el siguiente comando si es la primera vez en hacer un pon:
```sh
mknod /dev/ppp c 108 0
```

Verificar que la VPN este levantada correctamente, debe aparecer la interface ppp0 al ejecutar
```sh
ifconfig
```

Enrutar las direcciones a la VPN
```sh
route add -net 172.30.80.0 netmask 255.255.255.0 dev ppp0
```

### c) Instalación de Git

Instalar Git
```sh
apt-get install git
```

## 2. Instalación de dependencias

-- EN PROCESO DE INTEGRACIÓN A FIRMADOR ESTATAL --

## 3. Instalación de la plataforma

### a) Obtención del proyecto


Recuperar el proyecto con usuario y contraseña sin certificado
```sh
git config --global http.sslVerify false
```

Clonar el repositorio en alguna carpeta
```sh
git clone https://gitlab.geo.gob.bo/servicios/busa
```

### b) Configuración del entorno

Copiar el archivo de configuración de ejemplo a uno nuevo 

```sh
cp busa/busa-cliente/src/main/resources/configuracion.properties.ejemplo busa/busa-cliente/src/main/resources/configuracion.properties
```


Editar el siguiente archivo configuracion.properties de acuerdo al ambiente que corresponda:

Donde las propiedades más relevantes son:

* **alias** El alias del certificado público de ADSIB
* **numeroCuenta** Número de cuenta de ADSIB
* **codigoUninet** Código UniNet de ADSIB
* **ip** Ip de conexión a Banco Unión
* **institución** Sigla de ADSIB
* **certificadoBU** Certificado público del Banco Unión
* **rutaKeyStore** Ruta del archivo keystore de la ADSIB
* **contrasenaKeyStore** Contraseña del keystore de la ADSIB
* **contrasenaClavePrivada** Contraseña de la llave privada de la ADSIB

Configurar el puerto que va a ser utilizado por busa-proxy

```sh
nano busa/busa-proxy/config/application.properties
```
si se requiere, cambiar el valor del puerto en la linea

```sh
server.port=9000
```


### c) Compilación del proyecto

Ingresar a la carpeta busa

```sh
cd busa
```

Compilar e instalar el proyecto
```sh
mvn clean install
```

Copiar el archivo de configuracion del puerto donde se ha generado el archivo .jar
```sh
cp busa-proxy/config/application.properties busa-proxy/target/
```


### d) Puesta en marcha

Ingresar a la carpeta busa-proxy/target

```sh
cd busa-proxy/target
```

Con la instalación del proyecto se ha generado un archivo jar en la carpeta target.
Ejecutar el jar generado:

```sh
java -jar busa-proxy.jar
```


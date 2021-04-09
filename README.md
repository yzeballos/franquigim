# franquigim
proyecto de muestra JSF JPA con Java

El ambiente de prueba fue un Linux/Ubuntu con un server GlassFish Server 5.1.0:
escuchando en el puerto 8080.

En el caso de glassfish se debe poner el jar
mysql-connector-java-8.0.22.jar
como libreria externa en el directoro:
../GlassFish_Server/glassfish/domains/domain1/lib/ext

Crear el siguiente esquema en una base de datos MySql:

CREATE DATABASE `franquigimsch` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `franquigimsch`.`sucursal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,  
  `airelibre` tinyint(1) DEFAULT NULL,
  `longitud` float(10,5) NOT NULL,
  `latitud` float(10,5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

Crear las siguientes tablas:

CREATE TABLE `franquigimsch`.`afiliado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,  
  `plan` varchar(1) DEFAULT NULL,
  `descuento` float(10,5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

Y se recomiendan los siguientes datos de prueba:

INSERT INTO `franquigimsch`.`sucursal` (`id`, `nombre`, `airelibre`, `longitud`, `latitud`) VALUES ('1', 'Sucursal1', '1', ' 	-56.188', ' 	-34.333');
INSERT INTO `franquigimsch`.`sucursal` (`id`, `nombre`, `airelibre`, `longitud`, `latitud`) VALUES ('2', 'SucursalCordon', '0', ' 	-56.18472', '-34.90925');
INSERT INTO `franquigimsch`.`sucursal` (`id`, `nombre`, `airelibre`, `longitud`, `latitud`) VALUES ('3', 'SucursalPalermo', '1', ' 	-56.178', ' 	-34.907');

INSERT INTO `franquigimsch`.`afiliado` (`id`, `nombre`, `plan`, `descuento`) VALUES ('1', 'Juancito', 'B', '50');
INSERT INTO `franquigimsch`.`afiliado` (`id`, `nombre`, `plan`, `descuento`) VALUES ('2', 'Tafarel', 'C', '10');

El servidor de base de datos MySql se asume en el puerto estandar (3306):

Importante: En "./franquigim/src/conf/persist.xml" Se debe agregar las propiedades de javax.persistence.jdbc.user y javax.persistence.jdbc.password, que en este ejemplo
tienen el valor de "root" para poder acceder a la base de datos MySql.

      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/franquigimsch?useSSL=false"/>
      <property name="javax.persistence.jdbc.user" value="root"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value="root"/>


Para compilar el proyecto y generar el .war:

ant -f ./franquigim -Dnb.internal.action.name=rebuild -DforceRedeploy=false -Dbrowser.context=./franquigim clean dist

El .war deberia de haber quedado en: ./dist

Se corre con:
ant -f ./franquigim -Dnb.internal.action.name=run -Ddirectory.deployment.supported=true -DforceRedeploy=false -Dnb.wait.for.caches=true -Dbrowser.context=./franquigim run

Abriendo automaticamente un navegador en: http://localhost:8080/franquigim/

Para que sea otra la url por defecto, hay que editar la propiedad: deploy.ant.client.url de ./franquigim/build.xml

Importante:
Errores importantes conocidos:

Si el servidor indica este error:
uy.sample.entities.Sucursal cannot be cast to uy.sample.entities.Sucursal
Se soluciona reiniciado el servidor y volviendo a correr la aplicacion con el comando de anterior de ant.

Esto seguramente es un problema de classloader duplicado por una incorrecta configuracion del EntityManager y JPA.

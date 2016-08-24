A platform egy standard Maven alkalmazás, az `exec` pluginnal futtatható. A Netty framework-re épült, nincs szükség Tomcat vagy hasonló könyvtárra.

A platform futtatásához szükség van egy MySQL szerverre, a gyökérkönyvtárban található egy minta adatbázis. A `persistence.xml` állományban konfigurálható a hozzáférés. Ehhez társul egy minta `vsimages` könyvtár, amelyet bárhol elhelyezhetünk a merevlemezen, ide menti a rendszer a feltöltött képeket. A könyvtár elérési útja a `config.properties` állományban állítható be.

A minta adatbázis három felhasználót tartalmaz: natgeo, animals és city_person névvel, `password` a jelszó mindegyiknél.

Az `config.properties` állományban a szerver ip címe is állítható, alapértelmezetten `localhost`.

A projekt két portot használ, 9092 websocket, 9093 http részére.

Minimális futtatáshoz a következők kellenek:
1. A MySQL szerver `vspf` nevű adatbázisába töltsük be a `dev_db.sql` szkriptet.
2. Módosítsuk a `config.properties` állomány `imageCache` mezőjét, mutasson a `vsimages` könyvtárra.
3. Futtassuk a szervert `mvn exec:java` paranccsal.
4. Futtassuk a klienst ugyanezen a számítógépen.

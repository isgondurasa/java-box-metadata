**java-box-metadata**

This application shows how to import metadata to box.com.
the main functionality:
> import and parse *.xlsx file

> attach some template to file (1 row - field names, 2 row - field types, 3 row-... - values. Last column should contain filenames)

> fill the attached template with values from loaded *.xlsx


to run, first you have to install some dependencies with maven:
> poi-ooxml-3.14

> box-java-sdk-2.1.1

copy config files from templates:
> cp org/svao/sumati/config/BoxConfig.java.default org/svao/sumati/config/BoxConfig.java

> cp org/svao/sumati/config/XlsxConfig.java.default org/svao/sumati/config/XlsxConfig.java


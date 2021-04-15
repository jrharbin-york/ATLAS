#!/bin/sh
/usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 -classpath /home/atlas/atlas/atlas-middleware/middleware-java/target/classes:/home/atlas/atlas/atlas-middleware/jar-dependencies/activemq-all-5.15.10.jar:/home/atlas/atlas/atlas-middleware/jar-dependencies/jackson-annotations-2.10.2.jar:/home/atlas/atlas/atlas-middleware/jar-dependencies/jackson-core-2.10.2.jar:/home/atlas/atlas/atlas-middleware/jar-dependencies/jackson-databind-2.10.2.jar:/home/atlas/atlas/atlas-middleware/jar-dependencies/javapoet-1.12.0.jar:/home/atlas/atlas/atlas-middleware/jar-dependencies/jts-core-1.18.0-SNAPSHOT.jar:/home/atlas/atlas/atlas-middleware/jar-dependencies/opencsv-5.0.jar:/home/atlas/.m2/repository/org/apache/activemq/activemq-broker/5.15.10/activemq-broker-5.15.10.jar:/home/atlas/.m2/repository/org/apache/activemq/activemq-client/5.15.10/activemq-client-5.15.10.jar:/home/atlas/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/home/atlas/.m2/repository/org/apache/geronimo/specs/geronimo-jms_1.1_spec/1.1.1/geronimo-jms_1.1_spec-1.1.1.jar:/home/atlas/.m2/repository/org/fusesource/hawtbuf/hawtbuf/1.11/hawtbuf-1.11.jar:/home/atlas/.m2/repository/org/apache/geronimo/specs/geronimo-j2ee-management_1.1_spec/1.0.1/geronimo-j2ee-management_1.1_spec-1.0.1.jar:/home/atlas/.m2/repository/org/apache/activemq/activemq-openwire-legacy/5.15.10/activemq-openwire-legacy-5.15.10.jar:/home/atlas/.m2/repository/com/google/guava/guava/28.0-jre/guava-28.0-jre.jar:/home/atlas/.m2/repository/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:/home/atlas/.m2/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:/home/atlas/.m2/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:/home/atlas/.m2/repository/org/checkerframework/checker-qual/2.8.1/checker-qual-2.8.1.jar:/home/atlas/.m2/repository/com/google/errorprone/error_prone_annotations/2.3.2/error_prone_annotations-2.3.2.jar:/home/atlas/.m2/repository/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar:/home/atlas/.m2/repository/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17.jar:/home/atlas/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.10.2/jackson-databind-2.10.2.jar:/home/atlas/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.10.2/jackson-annotations-2.10.2.jar:/home/atlas/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.10.2/jackson-core-2.10.2.jar:/home/atlas/.m2/repository/com/squareup/javapoet/1.12.0/javapoet-1.12.0.jar:/home/atlas/.m2/repository/com/opencsv/opencsv/5.0/opencsv-5.0.jar:/home/atlas/.m2/repository/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar:/home/atlas/.m2/repository/org/apache/commons/commons-text/1.7/commons-text-1.7.jar:/home/atlas/.m2/repository/commons-beanutils/commons-beanutils/1.9.4/commons-beanutils-1.9.4.jar:/home/atlas/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar:/home/atlas/.m2/repository/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar:/home/atlas/.m2/repository/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar:/home/atlas/.m2/repository/org/locationtech/jts/jts-core/1.18.0/jts-core-1.18.0.jar:/home/atlas/.m2/repository/org/uma/jmetal/jmetal-core/5.10/jmetal-core-5.10.jar:/home/atlas/.m2/repository/org/apache/maven/reporting/maven-reporting-api/3.0/maven-reporting-api-3.0.jar:/home/atlas/.m2/repository/org/apache/maven/doxia/doxia-sink-api/1.0/doxia-sink-api-1.0.jar:/home/atlas/.m2/repository/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar:/home/atlas/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:/home/atlas/.m2/repository/org/hamcrest/hamcrest/2.2/hamcrest-2.2.jar:/home/atlas/.m2/repository/org/knowm/xchart/xchart/3.2.2/xchart-3.2.2.jar:/home/atlas/.m2/repository/de/erichseifert/vectorgraphics2d/VectorGraphics2D/0.11/VectorGraphics2D-0.11.jar:/home/atlas/.m2/repository/nz/ac/waikato/cms/weka/weka-stable/3.8.1/weka-stable-3.8.1.jar:/home/atlas/.m2/repository/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b/2015.03.26/java-cup-11b-2015.03.26.jar:/home/atlas/.m2/repository/nz/ac/waikato/cms/weka/thirdparty/java-cup-11b-runtime/2015.03.26/java-cup-11b-runtime-2015.03.26.jar:/home/atlas/.m2/repository/nz/ac/waikato/cms/weka/thirdparty/bounce/0.18/bounce-0.18.jar:/home/atlas/.m2/repository/com/googlecode/matrix-toolkits-java/mtj/1.0.4/mtj-1.0.4.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_ref-osx-x86_64/1.1/netlib-native_ref-osx-x86_64-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/native_ref-java/1.1/native_ref-java-1.1.jar:/home/atlas/.m2/repository/com/github/fommil/jniloader/1.1/jniloader-1.1.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_ref-linux-x86_64/1.1/netlib-native_ref-linux-x86_64-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_ref-linux-i686/1.1/netlib-native_ref-linux-i686-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_ref-win-x86_64/1.1/netlib-native_ref-win-x86_64-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_ref-win-i686/1.1/netlib-native_ref-win-i686-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_ref-linux-armhf/1.1/netlib-native_ref-linux-armhf-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_system-osx-x86_64/1.1/netlib-native_system-osx-x86_64-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/native_system-java/1.1/native_system-java-1.1.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_system-linux-x86_64/1.1/netlib-native_system-linux-x86_64-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_system-linux-i686/1.1/netlib-native_system-linux-i686-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_system-linux-armhf/1.1/netlib-native_system-linux-armhf-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_system-win-x86_64/1.1/netlib-native_system-win-x86_64-1.1-natives.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/netlib-native_system-win-i686/1.1/netlib-native_system-win-i686-1.1-natives.jar:/home/atlas/.m2/repository/net/sourceforge/f2j/arpack_combined_all/0.1/arpack_combined_all-0.1.jar:/home/atlas/.m2/repository/com/googlecode/netlib-java/netlib-java/1.1/netlib-java-1.1.jar:/home/atlas/.m2/repository/com/github/fommil/netlib/core/1.1/core-1.1.jar:/home/atlas/.m2/repository/com/github/mbuzdalov/non-dominated-sorting-implementations/0.2.1/non-dominated-sorting-implementations-0.2.1.jar:/home/atlas/.m2/repository/org/uma/jmetal/jmetal-exec/5.9/jmetal-exec-5.9.jar:/home/atlas/.m2/repository/org/uma/jmetal/jmetal-problem/5.9/jmetal-problem-5.9.jar:/home/atlas/.m2/repository/org/uma/jmetal/jmetal-lab/5.10/jmetal-lab-5.10.jar:/home/atlas/.m2/repository/tech/tablesaw/tablesaw-core/0.38.1/tablesaw-core-0.38.1.jar:/home/atlas/.m2/repository/it/unimi/dsi/fastutil/8.3.0/fastutil-8.3.0.jar:/home/atlas/.m2/repository/org/roaringbitmap/RoaringBitmap/0.8.12/RoaringBitmap-0.8.12.jar:/home/atlas/.m2/repository/org/roaringbitmap/shims/0.8.12/shims-0.8.12.jar:/home/atlas/.m2/repository/com/univocity/univocity-parsers/2.8.4/univocity-parsers-2.8.4.jar:/home/atlas/.m2/repository/com/ibm/icu/icu4j/65.1/icu4j-65.1.jar:/home/atlas/.m2/repository/io/github/classgraph/classgraph/4.8.60/classgraph-4.8.60.jar:/home/atlas/.m2/repository/tech/tablesaw/tablesaw-jsplot/0.38.1/tablesaw-jsplot-0.38.1.jar:/home/atlas/.m2/repository/io/pebbletemplates/pebble/3.1.2/pebble-3.1.2.jar:/home/atlas/.m2/repository/org/unbescape/unbescape/1.1.6.RELEASE/unbescape-1.1.6.RELEASE.jar:/home/atlas/.m2/repository/com/github/haifengl/smile-core/2.0.0/smile-core-2.0.0.jar:/home/atlas/.m2/repository/com/github/haifengl/smile-data/2.0.0/smile-data-2.0.0.jar:/home/atlas/.m2/repository/com/github/haifengl/smile-math/2.0.0/smile-math-2.0.0.jar:/home/atlas/.m2/repository/com/github/haifengl/smile-graph/2.0.0/smile-graph-2.0.0.jar:/home/atlas/.m2/repository/com/github/haifengl/smile-netlib/2.0.0/smile-netlib-2.0.0.jar:/home/atlas/.m2/repository/com/github/haifengl/smile-plot/2.0.0/smile-plot-2.0.0.jar:/home/atlas/.m2/repository/org/swinglabs/swingx/1.6.1/swingx-1.6.1.jar:/home/atlas/.m2/repository/com/jhlabs/filters/2.0.235/filters-2.0.235.jar:/home/atlas/.m2/repository/org/swinglabs/swing-worker/1.1/swing-worker-1.1.jar:/home/atlas/.m2/repository/org/uma/jmetal/jmetal-algorithm/5.10/jmetal-algorithm-5.10.jar:/home/atlas/.m2/repository/com/fuzzylite/jfuzzylite/5.0/jfuzzylite-5.0.jar:/home/atlas/.m2/repository/org/uma/jmetal/jmetal-auto/5.10/jmetal-auto-5.10.jar atlascollectiveintgenerator.runner.CollectiveIntRunner $1

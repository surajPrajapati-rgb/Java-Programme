#Variables
java_class_name="FileReaderDir"
directory_name="/home/surajprajapati/Downloads/SampleCorpus"

#compile
javac "$java_class_name".java

java "$java_class_name" "$directory_name"

rm "$java_class_name".class

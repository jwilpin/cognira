## Word Count

Count the occurrences of unique words in a text document.
**Input**: text document containing one or more paragraphs.
**Output**: list of unique words and count of occurrences, sorted by greatest count.

### running

    sbt runMain WordCountApp <file name>

By default, the text document located in *src/main/resources/word_count.txt* has the following content:

> Alpha zeta gamma alpha delta zeta alpha.

The expected list of output:

> alpha 3   
> zeta 2   
> gamma 1  
> delta 1

    sbt "runMain WordCountApp"
    [info] running WordCountApp 
    alpha 3
    zeta 2
    delta 1
    gamma 1

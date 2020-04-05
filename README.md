# tesco-tale-reader

Hope you all are safe and doing well.

Please let me share my first version of the solution for the given Tale-Reader Problem. The code is attached as a zip folder along with this email.

Design Principles Used:

Single Responsibility:
I have designed the solution by following the single-responsibility principle where each of my three classes is carrying out one and only one responsibility.
FileParser: Parse the content of Tale Input File
TaleProcessorSingleton: Process the tale content
TaleReaderOrchestrator: Orchestrate the tale-reader flow.


Scalability:
I have used FileInputStream to read the tale from the input file line by line. This design strategy can help our system to scale up efficiently while reading an extremely large file because we are not storing the content into memory. 

Singleton Principle:
I have used the TaleProcessorSingleton class as a Singleton class. I have implemented the singleton by using thread-safe double checking mechanism. This helps us to ensure that there will always be a maximum one resultant word-hashmap containing the output even-though we process the tale-data in parallel.

ConcurrentHashMap:
I have used ConcurrentHashMap to store the output table after processing the tale to make it thread-safe.


VCS :
First of all, I would like to apologize that I could not commit the code into my personal git repository and share with you from there. Actually, my personal laptop is in Bangalore and I am in my hometown i.e. Kolkata. In the midst of national lockdown, I am currently stuck here without access to my personal laptop. 
Hence, I am working on this project on my office laptop. And, to restrict security breach, they have blocked access to public github accounts. Hence, I could not upload it there. I would rather use this email chain to demonstrate my step by step commits virtually. Apologies for the inconvenience.

Output:

Please find below the sample output of my program where the individual word-counts given as test-cases like Donkey, The etc. are matching accurately, but I am getting a difference in the total number of words.


********* Tale Reader Output *********
The document contains 1193 words.
"Water" has been found 1 times.
"Heart" has been found 1 times.
"Practiced" has been found 1 times.
"Turned" has been found 1 times.
"Left" has been found 1 times.
"Donkey" has been found 19 times.
"Jewelry" has been found 1 times.
……………..
……………..
……………..
"I" has been found 18 times.
"Am" has been found 5 times.
"An" has been found 4 times.
"The" has been found 82 times.
"Played" has been found 1 times.
………………
……………...
"At" has been found 8 times.
"Gatekeeper's" has been found 1 times.
"Mind" has been found 1 times.
……………....
………………
"Year" has been found 1 times.
"Mirror-like" has been found 1 times.
"By" has been found 2 times.
"Your" has been found 4 times.
"Father" has been found 1 times.
"Fond" has been found 1 times.
……………….
……………….
"Made" has been found 1 times.
"Difficult" has been found 1 times.
"He" has been found 43 times.
"Went" has been found 3 times.
………………. And so on …………………

Process finished with exit code 0

My Understanding and Assumptions:
For Feature 1 (Word Count) : I am producing the sum of the number of words present in the tale which are meaningful. The definition of meaningful means words which do not contain any Special Character except -(Hyphen) and ‘(apostrophe).
For Feature 2 (Word Table): I am producing the output with all distinct meaningful words along with their individual count in given format.

Please let me know if my understanding for Feature 1 is correct? 

I have written this entire application using core-java principles avoiding use of framework like Spring. This is a command-line application which expects a single input string i.e. the absolute path of the input file.

I am yet to write down test-cases and few optimization strategies on this piece of code. But, I thought of sharing my approach and initial output details along with the code to ensure I am aligned with your expectations. 

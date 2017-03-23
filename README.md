#Overwatch Hero Suggestor


https://teamwatch.herokuapp.com/
https://github.com/sangheili868/teamwatch 
Chris Maheu
Matt Paulson


To run: use “lein run” in the root directory, with leiningen installed. Then go to port localhost:8080 in any browser


        This project is a webapp that uses data about Overwatch heroes to suggest which hero you should play in a given match. Overwatch is a first person shooter game with a variety of different heroes that have different strengths and weaknesses. Each player on two teams of six plays as one of the heroes. With this app, you input which heroes the enemy team and your teammates are playing and it suggests to you the hero best suited to counter the enemy team without having you choose a hero that is already on your team.
        
Before beginning this project, I wondered how functional programming could be helpful for website design at all. When I think of Lisp, I think of about people who use command lines and linux, not web developers or graphic interfaces. However, as we worked on this project, the power of Clojure made several tasks much easier. For example, creating the data structure that held the counters for each hero was quite straightforward. In other languages, we would probably have needed to worry about typing and memory allocation, as well as making a complex class and assigning all the values to it. In Clojure, we simply initialized some lists and hashmaps within hashmaps using brackets and curly braces (https://github.com/sangheili868/teamwatch/blob/master/src/clojure_getting_started/views/contents.clj#L14). We build a complex structure and spent very little time worrying about syntax or storage. Also, because functional programming guarantees no side effects, we did not have to worry about this structure being modified by a bug or error in any way. We could pass it to any functions we created and they could use it in any way needed.


Clojure also made the primary logic of our program simple, especially calculating which hero would be best to use (https://github.com/sangheili868/teamwatch/blob/master/src/clojure_getting_started/views/contents.clj#L86). It was easy to take the problem one step at a time, passing data from one function to another. First, we create a map with keypairs for each hero with a value of zero. Then, we create a map with each hero countered by each enemy hero, giving these values of negative one. We next create a map with each counter for each enemy hero, giving these values of positive one. Clojure easily concatenates these maps into a list of maps, then the powerful apply function is used to combine all the maps in the list, adding values when the keys match. Finally, we retrieve the key name with the maximum value, and display that hero. Clojure’s list manipulation tools made this significantly more simple than the same process might be in C++ or Python.
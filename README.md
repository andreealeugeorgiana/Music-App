AM FOLOSIT REZOLVAREA ETAPEI 2

Am implementat Design Pattern-ul Observer pentru a actualiza lista de melodii ascultate de un user de fiecare data cand se trece la urmatoarea melodie. Aceasta utilizare a Observer implica 
  * adaugarea interfetelor:
    - AudioListener (implementata de User) cu rolul de Observer si Concrete Observer
    - ObservablePlayer (implementata de Player) cu rolul de Subject si Concrete Subject
In clasa User, metoda updateListens se actualizeaza hashmap-ul care retine numarul de ascultari pentru fiecare melodie(si fiecare gen doar pentru user) ascultata de user, a artistului acelei melodii sau daca e podcast, doar a host-ului.


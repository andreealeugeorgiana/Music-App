Pentru a tine cont de sistemul de pagini, am adaugat un nou pachet, in care am definit doua clase :
- ArtistPage
- HostPage

Pentru a gestiona paginile specifice doar unui user normal, am impletentat Strategy Design Pattern in pachetul
- UserOnlyPages

Unde am definit o interfata Recommendations care are trei metode. Acestea sunt implementate in clasele:
- HomePageRecommendations
- LikedPageRecommendations

Cele trei metode: getRecommendedSongs, getRecommendedPlaylists si displayCurrentPage sunt implementate diferit in functie de tipul paginii.
- getRecommendedSongs returneaza o lista de melodii recomandate pentru pagina curenta (pentru HomePage sunt recomandate primele 5 melodii cele mai apreciate la care a dat el like, iar pentru LikedPage sun recomandate toate melodiile la care a dat like)
- getRecommendedPlaylists returneaza o lista de playlisturi recomandate pentru pagina curenta (pentru HomePage sunt recomandate primele 5 playlisturi cele mai apreciate la care a dat el Follow, iar pentru LikedPage sun recomandate toate playlisturile la care a dat like)
- displayCurrentPage afiseaza pagina curenta (HomePage sau LikedPage) si recomandarile pentru aceasta.

In clasa User am adaugat un atribut de tip Recommendations, care este initializat in functie de tipul paginii curente(determinat si el cu ajutorul unui field "type" ce poate fi "HomePage" sau "LikedPage").

Pentru continutul paginilor ArtistPage si HostPage, am adaugat un alt pachet numit "Extras", in care am definit trei clase: Event, Merch si Announcements, fiecare continand gettere, settere si field-uri specifice pentru informatiile ce urmeaza sa fie adaugate in aceste pagini.
ArtistPage contine o lista de Event si o lista de Merch, iar HostPage o lista de Announcements.
Fiecare din aceste doua clase are o metoda "display" care afiseaza informatiile despre evenimente, merch sau anunturi in formatul cerut in cerinta (folosing StringBuilder).
De asemenea, aceste doua clase au si un artist/host ca atribut, pentru a putea fi identificate mai usor.

Clasele User, Artist si Host sunt extensii ale clasei GeneralUser. (clasa user contine si field-ul "status" care indica daca este offline sau online).

Clasa GeneralUser contine field-urile comune tuturor celor trei tipuri de useri, precum si gettere si settere pentru acestea.

Clasa Artist contine un atribut de tip ArtistPage si un ArrayList de albume Album, iar clasa Host contine un atribut de tip HostPage si un ArrayList de Podcasturi.

Clasa Album face si ea parte din pachetul Collections si extinde clasa AudioCollection. Contine un atribut de tip ArrayList de melodii Song, un atribut de tip String pentru descirere, unul de tip Integer pentru anul lansarii releaseYear, int timestamp si nume si artist mostenite de la clasa AudioCollection .

Clasa Admin gestioneaza toate acestea, asa ca am adaugat in ea o lista de Host, Artist si Albume.

Metoda "addUser" din clasa Admin adauga un User, Artist sau Host in functie de tipul furnizat si ii adauga in listele corespunzatoare lor din clasa Admin.

"deleteUser" sterge un user normal doar daca nu exista un alt user care sa asculte la momentul curent o meloie din unul din playlist-urile facute de acesta. Daca acesta se poate sterge, atunci i se sterg playlisurile din lista de followedPlaylists a tuturor userilor, si se scade numarul de followeri a playlisturilor la care acesta( user-ul sterd) a dat like, ca mai apoi, el sa fie sters din lista de useri din Admin.
Pentru a sterge un artist, verificam mai intai daca se afla cineva pe pagina acestuia sau daca exista cineva care la momentul curect asculta o melodie dintr-un playlist de-al lui. Daca acesta se poate sterge, stergem mai intai melodiile artistului din likedSongs a tuturor userilor, pentru ca mai apoi sa stergem melodiile acestuia din lista de songs din Admin, albumele din lista de albume si in final, artistul din lista de artisti.
Similar se intampla si pentru un host, doar ca verificam daca exista un user care asculta la momentul actual un episod dintr-un podcast al hostului.

"showAlbums" si "showPodcasts" afiseaza toate albumele si podcasturile din lista de albume si podcasturi a unui user/host pe care il cauda in listele repsective fiecaruia..

"getOnlineUsers" returneaza o lista de useri care sunt online la momentul curent.
"getAllUsers" returneaza o lista de toti userii, de toate tipurile din Admin.
"getTop5Artists" returneaza o lista de top 5 artisti, adica cei mai apreciati artisti(cei care au cea mai mare suma de likeuri din toate melodiile din toate albumele).
"getTop5Albums" returneaza o lista de top 5 albume, adica cele mai apreciate albume(cele care au cea mai mare suma de likeuri din toate melodiile din acel album).

"addAlbum" adauga un album in lista de albume atat a unui Artist, cat si a Adminului. De asemenea, melodiile din album sunt adaugate si ele in lista de melodii din Admin.

"removeAlbum" sterge un album al unui artist doar daca nu exista un alt user care sa asculte la momentul actual o melodie din albumul care trebuie sters, sau daca niciun user nu are vreo melodie din acest album intr-un playlist. Daca se poate sterge, acesta este sters din lista de albume atat din Artist cat si din Admin, se sterg si melodiile din acel album din list de melodii din Admin.

"addEvent" adauga un eveniment pe pagina unui artist. "removeEvent" sterge un eveniment de pe pagina unui artist.
"addMerch" adauga un produs de merch pe pagina unui artist.

"addPodcast" adauga un podcast in lista de podcasturi a unui host si in lista de podcasturi a Adminului.

"removePodcast" sterge un podcast din lista de podcasturi a unui host si din lista de podcasturi a Adminului daca nu exista un user care sa aiba pe load in acel moment un podcast de-al hostului.

"addAnnouncement" adauga un anunt pe pagina unui host."removeAnnouncement" sterge un anunt de pe pagina unui host.

"switchConnectionStatus" schimba statusul unui user din online in offline si invers.

"changePage" schimba pagina curenta a unui user (cea de tip UserOnlyPage, care foloseste Strategy) in functie de "nextPage". Se seteaza instanta recommendations din page pentru pagina ceruta (fie Home, fie Liked) si se seteaza pe null artistPage si hostPage.

"printCurrentPage" afiseaza pagina curenta a unui user, folosind metoda displayCurrentPage din interfata Recommendations. (daca atat artistPage cat si hostPage sunt nule. in caz contrar, se afiseaza folosind metoda de display specifica acestor doua pagini, una dintre acestea).


In clasa SearchBar am mai adaugat, de asemenea, si cazurile de a caut un album, artist sau host. De asemenea, in clasa user, am adaugat si cazul de select a unui album/host/artist. Daca se selecteaza un host sau artist, se va initializa ori artistPage, ori hostPage cu un nou obiect de acel tip pentru artist-ul selectat, si celelat se va seta pe null (adica daca selectam un atist, atunci hosPage = null.)

Fiecare noua metoda este apelata in CommandRunner si in Main.

Am implementat Design Pattern-ul Observer pentru a actualiza lista de melodii ascultate de un user de fiecare data cand se trece la urmatoarea melodie. Aceasta utilizare a Observer implica 
  * adaugarea interfetelor:
    - AudioListener (implementata de User) cu rolul de Observer si Concrete Observer
    - ObservablePlayer (implementata de Player) cu rolul de Subject si Concrete Subject
In clasa User, metoda updateListens se actualizeaza hashmap-ul care retine numarul de ascultari pentru fiecare melodie(si fiecare gen doar pentru user) ascultata de user, a artistului acelei melodii sau daca e podcast, doar a host-ului.


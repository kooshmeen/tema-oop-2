#### Nume: Tonita Cosmin Cristian
#### Grupa: 321CD

#### Mentiuni: Am folosit solutia oficiala de la etapa 1. Am folosit in mare parte ChatGPT sub forma de GitHub copilot, care a ajutat mai mult ca un autocomplete++ la lucruri precum comentariile javadoc sau scrierea unor metode din classRunner, nu sa imi furnizeze logica, mai putin la formatarea outputurilor de la clasele Page, unde am si comentat acest lucru.

# Implementare

## Structura
Am modificat structura scheletului:
  * Am adaugat interfata Factory si clasele ArtistUserFactory, RegularUserFactory, HostUserFactory, care implementeaza Factory.
  * Am adaugat clasa Artist si Host, care extind User.
  * Am adaugat clasa Page, pe care am extins-o in clasele ArtistPage, HostPage, HomePage si LikedContentPage, in care se afiseaza outputul conform cerintei.
  * Am adaugat clasele Event, Merch si Announcement, utilizate in clasele Artist si Host.
  * Am adaugat clasele Album (ce contine melodiile unui artist) si AlbumOutput (similar ca la PlaylistOutput, pentru a fi mai usor outputul albumului in implementarea ArtistPage).

## Interactiuni
  * Clasele factory sunt folosite pentru a instantia obiectele de tip User si subtipurile lor cu acest design pattern.
  * Outputurile de pus in fisierul JSON sunt trecute de clasa CommandRunner, insa aceasta primeste rezultatul functiei/actiunii selectate prin clasa Admin, care are access la toate campurile necesare. Logica in sine se afla de regula in clasele User, daca actiunea efectuata este una specifica utilizatorului normal, de exemplu, sau in clasa Admin, cand actiunea este de tip generala, nu necesita inputul unui utilizator (spre exemplu, afisarea tuturor utilizatorilor online).
  * Pentru a simplifica sincronizarea paginilor artistilor/hostilor cu orice schimbari care pot aparea, clasele de tip Page nu retin informatii, ci doar le prelucreaza pe baza campurilor din clasele de care depind (de exemplu, clasa ArtistPage depinde de un obiect de tip Artist). Asa cum am mentionat, pentru formatarea acestor stringuri m-am folosit de ChatGPT.
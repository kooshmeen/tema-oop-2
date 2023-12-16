#### Nume: Tonita Cosmin Cristian
#### Grupa: 321CD

#### Mentiuni: Am folosit solutia oficiala de la etapa 1. Am folosit in mare parte ChatGPT sub forma de GitHub copilot, mai putin la formatarea outputurilor de la clasele Page, unde am si comentat acest lucru.

# Implementare

## Structura
Am modificat structura scheletului:
  * Am adaugat interfata Factory si clasele ArtistUserFactory, RegularUserFactory, HostUserFactory, care implementeaza Factory.
  * Am adaugat clasa Artist si Host, care extind User.
  * Am adaugat clasa Page, pe care am extins-o in clasele ArtistPage, HostPage, HomePage si LikedContentPage, in care se afiseaza outputul conform cerintei.
  * Am adaugat clasele Event, Merch si Announcement, utilizate in clasele Artist si Host.
  * Am adaugat clasele Album (ce contine melodiile unui artist) si AlbumOutput (similar ca la PlaylistOutput, pentru a fi mai usor outputul albumului in implementarea ArtistPage).

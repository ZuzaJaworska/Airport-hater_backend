# Airport Hater App
#### If airports are your nemesis, you're in the right place!

## Link do aplikacji frontendowej:

https://github.com/ZuzaJaworska/Airport-hater_frontend

### Link do ostatniego commita aplikacji frontendowej:

https://github.com/ZuzaJaworska/Airport-hater_frontend/commit/1f48248184c716e631540853460666700688a3cb

### Opis aplikacji
Aplikacja służy do komentowania i oceniania lotnisk, a dane o użytkownikach, lotniskach i komentarze są zapisywane lokalnie w bazie danych.
* Wykorzystano każdą z poznanych metod HTTP: GET, POST, PUT oraz DELETE.
* Wykorzystano dwa źródła zewnętrzne:
    * Airports By API Venue - aby pozyskać oficjalne dane o lotniskach i zapisać je w naszej aplikacji;
    * WeatherAPI.com By WeatherAPI.com - aby pozyskać dane o pogodzie panującej w czasie rzeczywistym na danym lotnisku.
* Zastosowano scheduler do wysyłania maili na trzy okoliczności:
    * co tydzień w poniedziałek o 10 rano - cotygodniowy newsletter;
    * gdy zostaje dodane lotnisko do bazy danych z aplikacji zewnętrznej;
    * gdy średnia ocena jakiegoś lotniska spadnie poniżej 3.0/10.
* Do warstwy widoku wykorzystano bibliotekę Vaadin.

### Wymagania
Projekt wymaga bazy danych pod nazwą airport_hater z użytkownikiem i hasłem określonymi w pliku application.properties w aplikacji backendowej.

###  Instrukcja uruchomienia
Aby uruchomić program należy:
1. uruchomić aplikację backendową - aplikacja pracuje na porcie 8080
2. następnie uruchomić aplikację frontendową - aplikacja pracuje na porcie 8888
3. żeby korzystać z programu w przeglądarce należy wejść na
   http://localhost:8888/
4. kolejność działań w aplikacji:
    * stworzenie użytkownika (bez tego kroku nie można dodać komentarza);
    * w zakładce Airports - pozyskanie danych o lotnisku poprzez wyszukanie go w zewnętrznym API - należy wyszukać lotnisko przez kod IATA i zatwierdzić przyciskiem "Search and Save", aby dane zostały zapisane w naszej bazie danych;
    * dodanie komentarza i ocenienie lotniska;
    * po tych podstawowych działaniach można zablokować użytkownika, odblokować użytkownika, sprawdzić pogodę dla danego lotniska, sprawdzać powiązania między komentarzami a użytkownikiem lub lotniskiem, kasować użytkowników, lotniska oraz komentarze.

###### Uwagi: Nie ma zabezpieczeń powstrzymujących przed kasowaniem użytkownika, lotniska i komentarza oraz nie ma ograniczeń dla zablokowanych użytkowników - będzie to dodane w przyszłości przez przypisanie użytkownikom ról admina lub zwykłego użytkownika.

### Życzę miłego oceniania lotnisk!

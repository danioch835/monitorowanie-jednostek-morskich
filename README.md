# monitorowanie-jednostek-morskich
Aplikacja do monitorowania jednostek morskich

# Funkcjonalności aplikacji
1. Aplikacja pozwala wyświetlić aktualne pozycje jednostek na podstawie danych pobranych z AIS.
2. Aplikacja pozwala wyświetlić trasę jednostek z ostatnich 24h na podstawie danych pobranych z AIS, które zostały zapisane w bazie danych. Oprócz wyrysowanych pozycji z ostatnich 24h aplikacja pokazuje również cel trasy (na podstawie danych pobranych z API positionstack).
3. Aplikacja pozwala wyświetlić historyczną trasę wybranej jednostki na podstawie danych z AIS, które zostały zapisane w bazie danych. Aktualnie historia trasy nie ma limitu czasowego, wyświetlane są wszystkie dane zapisane dla wybranej jednostki.

# Zasada działania aplikacji
1. Aktualne pozycje jednostek wyświetlane są na podstawie danych pobranych bezpośrednio z API AIS. Pobierane dane nie są w żaden sposób zapisywane, są tylko wyświetlane na mapie.
2. Historyczne pozycje jednostek pobierane są z API oraz zapisywane na bazie danych w celu ich późniejszego wyświetlenia. Wykorzystana została tutaj usługa zwracający pozycje dla wybranej jednostki z ostatnich 24h. Dane pobierane są co 10 sekund przez działające w tle zadanie. Dane zapisywanę są w bazie danych przyrostowo (nowsze pozycje niż te które znajdują się już na bazie).
3. Cel podrózy statków pobierany jest na na podstawie tekstu pobranego z AIS. Dane pobierane są z API positionstack. Znalezione miejsca wraz ze współrzędnymi zapisywane są na bazie danych w celu ograniczenia ruchu do zewnętrznego API. Podczas pobierania współrzędnych wykorzystywana jest najpierw wartosć zapisana w bazie danych, a jeśli odpowiedni wpis nie istnieje to wtedy następuje próba pobrania lokalizacji z API positionstack.

# Uruchomienie aplikacji
Aby uruchomić aplikację należy przy pomocy Docker uruchomić dwa kontenery. Pierwszy z bazą danych Postgres oraz drugi z aplikacją.

## Uruchomienie bazy danych
1. Uruchomienie kontenera z bazą danych
<pre>docker run -d --name some-postgres -v pgdata:/var/lib/postgresql/data -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 postgres</pre>

2. Podłączenie się do bazy danych w kontenerze:
<pre>
docker exec -it some-postgres psql -U postgres
</pre>
3. Utworzenie bazy na potrzeby aplikacji (baza o nazwie "test"): 
<pre>
create database test;
</pre>

<b>UWAGA! Należy ustawić własne hasło do bazy w zmiennej POSTGRES_PASSWORD</b>

## Zbudowanie i uruchomienie aplikacji
Aplikację należy zbudować z wykorzystaniem Java 11 oraz Maven 3.6.3 wykonując poniższe kroki.

1. Zbudowanie pliku JAR
<pre>
mvn clean package
</pre>

2. Zbudowanie obrazu Docker
<pre>
docker build -t monitorowanie-jednostek .
</pre>

3. Uruchomienie aplikacji w kontenerze
<pre>
docker run -d --name monitorowanie-jednostek -p 8084:8080 monitorowanie-jednostek
</pre>

<b>Aplikacja uruchomi się na porcie 8084.</b>

# Przykłady z działania aplikacji

1. Wyświetlenie aktualnych pozycji jednostek. URL http://localhost:8084/actual
![akualna_mapa](https://user-images.githubusercontent.com/26234920/144764507-a399ee2e-8b87-4360-889a-becd8b6cc566.PNG)

2. Wyświetlanie aktualnych pozycji jednostek, trasy z ostatnich 24h oraz celu podróży.URL http://localhost:8084/tracks

Po najechaniu na wybraną jednostkę na mapie pojawia się trasa z ostatnich 24h oraz cel podróży jeśli udało się go znaleźć.
![ostatnie_24h](https://user-images.githubusercontent.com/26234920/144764572-11b74816-96dc-48f1-90ec-a1291814538e.png)

Po kliknięciu w znacznik jednostki pozostałe znaczniki znikają, a na mapie zostaje tylko wybrany znacznik wraz z trasą z ostatnich 24h oraz celem podróży.
![ostatnie_24h_popup](https://user-images.githubusercontent.com/26234920/144764630-795fe154-d734-4d25-b18a-0d3b6bde7cca.PNG)

Njważniejsze funkcjonalnośći z powyższego zdjęcia:
1. Zielony znacznik oznacza aktualną pozycję jednostki.
2. Czarna linia prezentuje trasę z ostatnich 24h i łączy znacznik aktualnej pozycji jednostki z pierwszym znacznikiem pozycji.
3. Czerwona linia prezentuje połączenie aktualnej pozycji z celem podróży, który oznaczony jest również czarnym znacznikiem.
4. Po kliknięciu w pierszą oraz aktualną pozycję jednostki pojawia się popup z dodatkowymi informacjami o jednostce.

Popup z informacjami o jednostce zawiera następujące dane:
1. Nazwę jednostki
2. Dystans w metrach pokonany w ciągu ostatnich 24h
3. Cel podróży
4. Datę pobrania aktualnej pozycji
5. Link do pełnej histori trasy dla danej jednostki (po kliknięciu w link otwiera się w nowym oknie strona z historyczną trasą, która opisana jest w punkcie 3.

Po ponownym kliknięciu w znacznik na mapie pojawiają się pozostałe jednostki.

3. Wyświetlanie historycznej trasy jednostki. URL http://localhost:8084/tracks/{MMSI}
![historia](https://user-images.githubusercontent.com/26234920/144764854-2b754aca-42eb-474b-8a73-aa54e7823d53.png)

Czarna linia reprezentuje hostoryczną trasę jednostki. Po najechaniu na pierwszy oraz ostatni znacznik trasy wyświetlają się daty z których pochodzą pomiary. Czarny znacznik oznacza pierwszy pomiar natomiast zielony ostatni.


## Pełny przykład dla śledzenia trasy jednostki:

<b>a) Znajdujemy jednostkę na mapie i najeżdzamy na nią żeby zobaczyć jej trasę z ostatnich 24h oraz cel podróży:</b>
![ostatnie_24h](https://user-images.githubusercontent.com/26234920/144765599-276ebb1b-3afc-46c8-84ca-6b50b22f411f.png)

<b>b) Klikamy na znacznik jednostki aby usunąć pozostałe jednostki oraz wyświetlić popup z danymi o jednostce:</b>
![ostatnie_24h_popup](https://user-images.githubusercontent.com/26234920/144765631-74db3f52-957c-46d6-998f-ad7e5dbba86d.PNG)

<b>c) Klikamy w link "Show history" na poupie aby zobaczyć dotychczasową historię trasy jednostki:</b>
![historia](https://user-images.githubusercontent.com/26234920/144765686-ce5eb058-12f7-464c-bae3-ded9cbcea33c.png)

<b>d) Po dłuższym czasie ponownie klikamy w link, aby zobaczyć historię trasy jednostki:</b>
![historia_pelna](https://user-images.githubusercontent.com/26234920/144765689-4834b8df-d3dd-4fc0-963e-3491e611a174.PNG)

<b>Jak widać historia trasy jest zgodna z celem trasy widocznym na obrazie z punktu a oraz b.</b>

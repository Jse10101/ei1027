DROP TABLE isPayedBy;
DROP TABLE Request;
DROP TABLE Contract;
DROP TABLE Invoice;
DROP TABLE Availability;
DROP TABLE Company;
DROP TABLE Elderly;
DROP TABLE SocialWorker;
DROP TABLE Volunteer;
DROP TABLE Login;

-- T A B L A   V O L U N T A R I O
CREATE TABLE Volunteer (
	-- Atributos
	name VARCHAR(50) NOT NULL,
	dni VARCHAR(9) NOT NULL,
	pwd VARCHAR(20) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phoneNumber VARCHAR(9) NOT NULL,
	hobbies VARCHAR(200),
	applicationDate DATE NOT NULL,
	acceptationDate DATE,
	finishDate DATE,
	accepted BOOLEAN DEFAULT FALSE,
	birthDate DATE NOT NULL,
	-- Restricciones
	CONSTRAINT cp_volunteer PRIMARY KEY (dni),
	CONSTRAINT calt_volunteer_email UNIQUE (email),
	CONSTRAINT ri_volunteer_dni CHECK (LENGTH(dni) = 9),
	CONSTRAINT ri_volunteer_pwd CHECK (LENGTH(pwd) BETWEEN 8 AND 20),
	CONSTRAINT ri_volunteer_phoneNumber CHECK (LENGTH(phoneNumber) = 9),
	CONSTRAINT ri_volunteer_edad CHECK (
	    (DATE_PART('year',CURRENT_DATE)-DATE_PART('year',birthDate))*12 + (DATE_PART('month', CURRENT_DATE) - DATE_PART('month', birthDate)) >= 18*12)
);

-- T A B L A   T R A B A J A D O R   S O C I A L
CREATE TABLE SocialWorker (
	-- Atributos
	name VARCHAR(50) NOT NULL,
	userCAS VARCHAR(9) NOT NULL,
	pwd VARCHAR(20) NOT NULL,
	phoneNumber VARCHAR(9) NOT NULL,
	email VARCHAR(50) NOT NULL,
	-- Restricciones
	CONSTRAINT cp_socialWorker PRIMARY KEY (userCAS),
	CONSTRAINT calt_socialWorker_email UNIQUE (email),
	CONSTRAINT ri_socialWorker_userCAS CHECK (LENGTH(userCAS) = 9),
	CONSTRAINT ri_socialWorker_pwd CHECK (LENGTH(pwd) BETWEEN 8 AND 20),
	CONSTRAINT ri_socialWorker_phoneNumber CHECK (LENGTH(phoneNumber) = 9)
);

-- T A B L A   A N C I A N O S
CREATE TABLE Elderly (
	-- Atributos
	dni VARCHAR(9) NOT NULL,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	bankAccountNumber VARCHAR(19) NOT NULL,
	userpwd VARCHAR(20) NOT NULL,
	email VARCHAR(50) NOT NULL,
	phoneNumber VARCHAR(9) NOT NULL,
	birthDate DATE NOT NULL,
	dateCreation DATE NOT NULL,
	alergies VARCHAR(200),
	diseases VARCHAR(200),
	-- Claves ajenas
	userCAS_socialWorker VARCHAR(12),
	-- Restricciones
	CONSTRAINT cp_elderly PRIMARY KEY (dni),
	CONSTRAINT ca_socialWorker FOREIGN KEY (userCAS_socialWorker)
				REFERENCES SocialWorker(userCAS)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT calt_elderly_bankAccountNumber UNIQUE (bankAccountNumber),
	CONSTRAINT calt_elderly_email UNIQUE (email),
	CONSTRAINT ri_elderly_dni CHECK (LENGTH(dni) = 9),
	CONSTRAINT ri_elderly_bankaccount CHECK (LENGTH(bankAccountNumber) = 19),
	CONSTRAINT ri_elderly_pwd CHECK (LENGTH(userpwd) BETWEEN 8 AND 20),
	CONSTRAINT ri_elderly_phoneNumber CHECK (LENGTH(phoneNumber) = 9),
	CONSTRAINT ri_elderly_edad CHECK (
	    (DATE_PART('year',CURRENT_DATE)-DATE_PART('year',birthDate))*12 + (DATE_PART('month', CURRENT_DATE) - DATE_PART('month', birthDate)) >= 65*12),
	CONSTRAINT ri_elderly_userCAS CHECK (LENGTH(userCAS_socialWorker) = 9)
);


-- T A B L A   C O M P A Ã‘ I A
CREATE TABLE Company (
	-- Atributos
	name VARCHAR(30) NOT NULL,
	cif VARCHAR(9) NOT NULL,
	pwd VARCHAR(20) NOT NULL,
	address VARCHAR(50) NOT NULL,
	contactName VARCHAR(50) NOT NULL,
	contactPhoneNumber VARCHAR(9) NOT NULL,
	contactEmail VARCHAR(50) NOT NULL,
	serviceType VARCHAR(30) NOT NULL,
	-- Restricciones
	CONSTRAINT cp_company PRIMARY KEY (cif),
	CONSTRAINT ri_company_cif CHECK (LENGTH(cif) = 9),
	CONSTRAINT ri_company_pwd CHECK (LENGTH(pwd) BETWEEN 8 AND 20),
	CONSTRAINT ri_company_phoneNumber CHECK (LENGTH(contactPhoneNumber) = 9)

);

-- T A B L A   D I S P O N I B I L I D A D
CREATE TABLE Availability (
	-- Atributos
	fecha DATE NOT NULL,
	beginingHour TIME NOT NULL,
	endingHour TIME NOT NULL,
	stateAvailability BOOLEAN DEFAULT TRUE,
	-- Claves ajenas
	dni_volunteer VARCHAR(9),
	dni_elderly VARCHAR(9),
	-- Restricciones
	CONSTRAINT cp_availability PRIMARY KEY (dni_volunteer,fecha,beginingHour),
	CONSTRAINT ca_volunteer FOREIGN KEY (dni_volunteer)
				REFERENCES Volunteer(dni)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_elderly FOREIGN KEY (dni_elderly)
				REFERENCES Elderly(dni)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_availability_dni_volunteer CHECK (LENGTH(dni_volunteer) = 9),
	CONSTRAINT ri_availability_dni_elderly CHECK (LENGTH(dni_elderly) = 9)
);


-- T A B L A   F A C T U R A
CREATE TABLE Invoice (
	-- Atributos
	fecha DATE NOT NULL,
	idNumber VARCHAR(8) NOT NULL,
	amount INTEGER NOT NULL,
	concept VARCHAR(200),
	-- Claves ajenas
	dni_elderly VARCHAR(9) NOT NULL,
	-- Restricciones
	CONSTRAINT cp_invoice PRIMARY KEY (idNumber),
	CONSTRAINT ca_elderly FOREIGN KEY (dni_elderly)
				REFERENCES Elderly(dni)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_invoice_idNumber CHECK (LENGTH(idNumber) < 9),
	CONSTRAINT ri_invoice_amount CHECK (amount>=0),
	CONSTRAINT ri_invoice_dni_elderly CHECK (LENGTH(dni_elderly) = 9)

);

-- T A B L A   C O N T R A T O
CREATE TABLE Contract (
	-- Atributos
	idNumber VARCHAR(8) NOT NULL,
	dateBegining DATE NOT NULL,
	dateEnding DATE NOT NULL,
	serviceType VARCHAR(30) NOT NULL,
	price INTEGER NOT NULL,
	-- Claves ajenas
	cif_company VARCHAR(9) NOT NULL,
	-- Restricciones
	CONSTRAINT cp_contract PRIMARY KEY (idNumber),
	CONSTRAINT ca_company FOREIGN KEY (cif_company)
				REFERENCES Company(cif)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_contract_idNumber CHECK (LENGTH(idNumber) < 9),
	CONSTRAINT ri_contract_price CHECK (price>0),
	CONSTRAINT ri_contract_cif CHECK (LENGTH(cif_company) = 9)

);

-- T A B L A   P E T I C I Ó N
CREATE TABLE Request (
	-- Atributos
	idNumber VARCHAR(8) NOT NULL,
	serviceType VARCHAR(30) NOT NULL,
	creationDate DATE NOT NULL,
	state BOOLEAN DEFAULT FALSE,
	aprovedDate DATE,
	rejectedDate DATE,
	finishDate DATE,
	comentari VARCHAR(200),
	-- Claves ajenas
	dni_elderly VARCHAR(9) NOT NULL,
	idNumber_contract VARCHAR(8),
	-- Restricciones
	CONSTRAINT cp_request PRIMARY KEY (idNumber),
	CONSTRAINT ca_elderly FOREIGN KEY (dni_elderly)
				REFERENCES Elderly(dni)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_contract FOREIGN KEY (idNumber_contract)
				REFERENCES Contract(idNumber)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_request_idNumber CHECK (LENGTH(idNumber) < 9),
	CONSTRAINT ri_request_dni_elderlyr CHECK (LENGTH(dni_elderly) = 9),
	CONSTRAINT ri_request_idNumber_contract CHECK (LENGTH(idNumber_contract) < 9)
);

-- T A B L E   I S  P A Y E D  B Y   ( P E T I C I Ã“ N - F A C T U R A )
CREATE TABLE isPayedBy (
	-- Claves ajenas
	idNumber_invoice VARCHAR(8) NOT NULL,
	idNumber_request VARCHAR(8) NOT NULL,
	-- Restricciones
	CONSTRAINT cp_isPayedBy PRIMARY KEY (idNumber_invoice, idNumber_request),
	CONSTRAINT ca_invoice FOREIGN KEY (idNumber_invoice)
				REFERENCES Invoice(idNumber)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ca_request FOREIGN KEY (idNumber_request)
				REFERENCES Request(idNumber)
				ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT ri_ispayedby_idNumber_invoice CHECK (LENGTH(idNumber_invoice) < 9),
	CONSTRAINT ri_ispayedby_idNumber_request CHECK (LENGTH(idNumber_request) < 9)
);


-- T A B L E   L O G I N
CREATE TABLE Login (
	-- Atributos
    usuario VARCHAR(9) NOT NULL,
	pwd VARCHAR(20) NOT NULL,
	role VARCHAR(20) NOT NULL,
	-- Restricciones
	CONSTRAINT cp_login PRIMARY KEY (usuario),
	CONSTRAINT ri_login_user CHECK (LENGTH(usuario) = 9),
	CONSTRAINT ri_login_psw CHECK (LENGTH(pwd) BETWEEN 8 AND 20)
);



-- I N S E R T S   V O L U N T E E R
INSERT INTO Volunteer VALUES ('Diego Lacomba','12345678R','diegolacomba', 'diego@gmail.com', '123456789', 'deporte y videojuegos', '2019-04-09', NULL, NULL, FALSE, '1999-12-18');

INSERT INTO Volunteer VALUES ('Rafael Mesado','23456789Q','rafamesado', 'rafa@gmail.com', '234567890', 'deporte, videojuegos y leer', '2019-04-09', NULL, NULL, FALSE, '1999-10-23');

INSERT INTO Volunteer VALUES ('Jose Nebot','45678456T','josenebot', 'jose@gmail.com', '126789865', NULL, '2019-04-09', NULL, NULL, FALSE, '1998-09-20');

INSERT INTO Volunteer VALUES ('Adrián García','12345853F','adriangarcia', 'adrian@gmail.com', '123895386', 'cine', '2019-04-09', NULL, NULL, FALSE, '1996-02-18');

INSERT INTO Volunteer VALUES ('Perico Palotes','69420690W','pericopalotes', 'perico@gmail.com', '987654321', 'salir de parranda', '2019-04-09', NULL, NULL, FALSE, '1997-10-23');



-- I N S E R T S   S O C I A L   W O R K E R
INSERT INTO SocialWorker VALUES ('Tabajador Uno','worker001','trabajador1', '603927489', 'worker1@gmail.com');

INSERT INTO SocialWorker VALUES ('Tabajador Dos','worker002','trabajador2', '683227434', 'worker2@gmail.com');

INSERT INTO SocialWorker VALUES ('Tabajador Tres','worker003','trabajador3', '683927476', 'worker3@gmail.com');

INSERT INTO SocialWorker VALUES ('Tabajador Cuatro','worker004','trabajador4', '673927426', 'worker4@gmail.com');

INSERT INTO SocialWorker VALUES ('Tabajador Cinco','worker005','trabajador5', '573427475', 'worker5@gmail.com');



-- I N S E R T S   E L D E R L Y
INSERT INTO Elderly VALUES ('48273019S','Paco', 'García', 'C/ Ejemplo Uno', 'ES23876587658765000', 'pacogarcia', 'pacog@gmail.com', '234678963', '1945-06-25', '2019-04-09', NULL, NULL, NULL);

INSERT INTO Elderly VALUES ('58274356Y','Pepa', 'Gonzalez', 'C/ Ejemplo Dos', 'ES23234523452345000', 'pepagonzalez', 'pepag@gmail.com', '345765345', '1944-08-15', '2019-04-09', NULL, NULL, NULL);

INSERT INTO Elderly VALUES ('98561723I','Carmen', 'Marín', 'C/ Ejemplo Tres', 'ES24567456745670000', 'carmenmarin', 'carmenm@gmail.com', '678543123', '1947-02-25', '2019-04-09', NULL, NULL, NULL);

INSERT INTO Elderly VALUES ('58761324E','Julián', 'Bazán', 'C/ Ejemplo Cuatro', 'ES23423542354235000', 'julianbazan', 'julianb@gmail.com', '689574364', '1946-09-18', '2019-04-09', NULL, NULL, NULL);

INSERT INTO Elderly VALUES ('48523694L','Victor', 'Cayetano', 'C/ Ejemplo Cinco', 'ES23234567896789000', 'victorcayetano', 'victorc@gmail.com', '654321478', '1945-06-12', '2019-04-09', NULL, NULL, NULL);



-- I N S E R T S   C O M P A N Y
INSERT INTO Company VALUES ('MCarrones','E12345678','mcarrones', 'C/ Empresas Uno', 'Fernando Gil', '678678989', 'mcarrones@gmail.com', 'Menjar a domicili');

INSERT INTO Company VALUES ('Compañias','E12348765','comidassa', 'C/ Empresas Dos', 'Amparo Fernández', '600123456', 'comidassa@gmail.com', 'Acompanyament');

INSERT INTO Company VALUES ('DonLimpio','E12342897','donlimpio', 'C/ Empresas Tres', 'Calvo Anuncio', '789667564', 'dlimpio@gmail.com', 'Neteja');

INSERT INTO Company VALUES ('Quiropract','E12342345','quiropract', 'C/ Empresas Cuatro', 'Rafa Masaje', '694206923', 'masajito@gmail.com', 'Servei sanitari');

INSERT INTO Company VALUES ('DocsADom','E12344560','docsadom', 'C/ Empresas Cinco', 'Doctor López', '655446623', 'docs@gmail.com', 'Servei sanitari');


-- I N S E R T S   A V A I L A B I L I T Y
INSERT INTO Availability VALUES ('2020-05-23','19:30:00.00','21:30:00.00', TRUE, '12345678R', '48273019S');

INSERT INTO Availability VALUES ('2020-05-24','19:30:00.00','21:30:00.00', TRUE, '12345678R', '58274356Y');

INSERT INTO Availability VALUES ('2020-05-23','18:30:00.00','20:30:00.00', TRUE, '23456789Q', '48273019S');

INSERT INTO Availability VALUES ('2020-08-01','19:30:00.00','21:30:00.00', TRUE, '12345678R', '58761324E');

INSERT INTO Availability VALUES ('2020-05-02','19:30:00.00','21:30:00.00', TRUE, '12345678R', '58761324E');



-- I N S E R T S   I N V O I C E
INSERT INTO Invoice VALUES ('2016-03-13','28800',3, NULL, '48273019S' );

INSERT INTO Invoice VALUES ('2018-07-01','26000',1,NULL, '58274356Y' );

INSERT INTO Invoice VALUES ('2019-12-04','15500',2,NULL, '98561723I' );

INSERT INTO Invoice VALUES ('2019-06-13','10100',1,NULL,   '58761324E' );

INSERT INTO Invoice VALUES ('2017-05-16','90000',1,NULL, '48523694L' );



-- I N S E R T S   C O N T R A C T
INSERT INTO Contract VALUES ('10000','2016-01-01','2017-01-01','Acompanyament',20.0,'E12348765');

INSERT INTO Contract VALUES ('10100','2017-04-03','2025-04-03','Neteja',25.0,'E12342897');

INSERT INTO Contract VALUES ('10200','2018-02-01','2019-02-01','Neteja',25.0,'E12342897');

INSERT INTO Contract VALUES ('10300','2018-11-09','2022-11-09','Menjar a domicili',9.99,'E12345678');

INSERT INTO Contract VALUES ('10400','2016-03-01','2019-03-01','Servei sanitari',25.0,'E12344560');



-- I N S E R T S   R E Q U E S T
INSERT INTO Request VALUES ('1','Acompanyament','2016-01-01',FALSE,NULL,NULL,NULL,'comentario request 1','48273019S','10000');

INSERT INTO Request VALUES ('2','Neteja','2017-04-01',FALSE,NULL,NULL,NULL, 'comentari request 2','58274356Y','10100');

INSERT INTO Request VALUES ('3','Neteja','2018-01-02',FALSE,NULL,NULL,NULL, 'comentari request 3','98561723I','10200');

INSERT INTO Request VALUES ('4','Menjar a domicili','2018-11-09',TRUE,'2022-11-09',NULL,NULL, 'comentari request 4','58761324E','10300');

INSERT INTO Request VALUES ('5','Servei sanitari','2016-02-04',TRUE,'2019-02-04',NULL,NULL, 'comentari request 5','48523694L','10400');



-- I N S E R T S   I S   P A Y E D   B Y
INSERT INTO isPayedBy VALUES ('28800','1');

INSERT INTO isPayedBy VALUES ('26000','2');

INSERT INTO isPayedBy VALUES ('15500','3');

INSERT INTO isPayedBy VALUES ('10100','4');

INSERT INTO isPayedBy VALUES ('90000','5');




-- INSERTS LOGIN
INSERT INTO Login VALUES ('48273019S', 'pacogarcia', 'elderly');

INSERT INTO Login VALUES ('58274356Y', 'pepagonzalez', 'elderly');

INSERT INTO Login VALUES ('98561723I', 'carmenmarin', 'elderly');

INSERT INTO Login VALUES ('58761324E', 'julianbazan', 'elderly');

INSERT INTO Login VALUES ('48523694L', 'victorcayetano', 'elderly');


-- I N S E R T S   I S   P A Y E D   B Y 
INSERT INTO isPayedBy VALUES ('28800','1');

INSERT INTO isPayedBy VALUES ('26000','2');

INSERT INTO isPayedBy VALUES ('15500','3');

INSERT INTO isPayedBy VALUES ('10100','4');

INSERT INTO isPayedBy VALUES ('90000','5');




-- INSERTS LOGIN
INSERT INTO Login VALUES ('48273019S', 'pacogarcia', 'elderly');

INSERT INTO Login VALUES ('58274356Y', 'pepagonzalez', 'elderly');

INSERT INTO Login VALUES ('98561723I', 'carmenmarin', 'elderly');

INSERT INTO Login VALUES ('58761324E', 'julianbazan', 'elderly');

INSERT INTO Login VALUES ('48523694L', 'victorcayetano', 'elderly');

-- I N S E R T S   V O L U N T E E R ( L O G I N )
INSERT INTO Login VALUES ('12345678R','diegolacomba', 'volunter');

INSERT INTO Login VALUES ('23456789Q','rafamesado', 'volunter' );

INSERT INTO Login VALUES ('45678456T','josenebot', 'volunter' );'

INSERT INTO Login VALUES ('12345853F','adriangarcia', 'volunter' );'

INSERT INTO Login VALUES ('69420690W','pericopalotes', 'volunter' );'


-- I N S E R T S   S O C I A L   W O R K E R ( L O G I N )
INSERT INTO Login VALUES ('worker001','trabajador1', 'SocialWorker');

INSERT INTO Login VALUES ('worker002','trabajador2','SocialWorker');

INSERT INTO Login VALUES ('worker003','trabajador3', 'SocialWorker');

INSERT INTO Login VALUES ('worker004','trabajador4', 'SocialWorker');

INSERT INTO Login VALUES ('worker005','trabajador5', 'SocialWorker');





-- I N S E R T S   C O M P A N Y ( L O G I N )
INSERT INTO Login VALUES ('E12345678', 'MCarrones', 'Company')

INSERT INTO Login VALUES ('E12348765','Compañias', 'Company')

INSERT INTO Login VALUES ('E12342897','DonLimpio', 'Company')

INSERT INTO Login VALUES ('E12342345', 'Quiropract','Company')
INSERT INTO Login VALUES ('E12344560','DocsADom', 'Company')
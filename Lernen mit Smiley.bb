AppTitle "Lernen mit Smiley"

Global filename$
Global TB
Global ESPS
Global PROZ
Global LPro#


Include ".\ftp.bb"


.PStart
Grafik_Hauptmenue_verbieten=0

filename$=".\Grafik.txt"
Modus=GfxModeExists(1280,1024,16)
If Modus=0 Then
	If FileType(filename$)=0 Then
		ESPS=1
		fileout = WriteFile(".\Grafik.txt")
		WriteLine fileout,"Grafik: 1280,1024,16,3"
		CloseFile fileout
	EndIf
Else
	If FileType(filename$)=0 Then
		ESPS=1
		fileout = WriteFile(".\Grafik.txt")
		WriteLine fileout,"Grafik: 1280,1024,16,2"
		CloseFile fileout
	EndIf
EndIf


filein = ReadFile(".\Grafik.txt")
Grafik$ = ReadLine$(filein)
Pos=Instr(Grafik$," ")
Grafik$=Right$(Grafik$,Len(Grafik$)-Pos)

Pos1=Instr(Grafik$,",")
Grafik1$=Left$ (Grafik$,Pos1-1)
Pos2=Instr(Grafik$,",",Pos1+1)
Grafik2$=Mid$ (Grafik$,Pos1+1,(Pos2-Pos1)-1)
Pos3=Instr(Grafik$,",",Pos2+1)
Grafik3$=Mid$ (Grafik$,Pos2+1,(Pos3-Pos2)-1)
Pos4=Instr(Grafik$,",",Pos3+1)
Grafik4$=Mid$ (Grafik$,Pos3+1,(Pos4-Pos3)-1)

CloseFile filein

Modus=GfxModeExists(1280,1024,16)
If Modus=True Then Graphics 1280,1024,16,2
Graphics Grafik1$,Grafik2$,Grafik3$,Grafik4$



;Verbindungstest steht schon hier, da es mit nur "Lernen mit smiley" komisch
;aussehen würde und dis dann eh nur wenige Tausendstelsekunden Sichtbar währe!

TB=LoadImage(".\Bilder\Titelbild.jpg")
SchriftLMSS = LoadFont ("Arial",120,True)
TileBlock TB
SetFont SchriftLMSS
Color 0,0,0
Text 640,390,"Lernen mit Smiley",True
Text 640,520,"Verbindungstest",True
Delay 100
VWait


Function LProA()
TileBlock TB
Text 640,520,Left (LPro#,Instr(LPro#,".")-1)+"%",True
LPro#=LPro#+0.77
VWait
End Function


;AppTitle "FirePaint!"
; verified 1.48 4/18/2001

Const intensity=20	;play with this number!
;My P3-666 can handle '40' without dropping a frame (debug off)
;This looks AWESOME!


Const gravity#=.1

Type Frag
	Field x#,y#,xs#,ys#
	Field r,g,b
End Type


Const ZeitMaxSLMS = 1500  ; 1.5 Sekunden
Const width=1280,height=1024
Const xsize=16
Const ysize=16
Const xdiv=width/xsize
Const ydiv=height/ysize
Const total=xdiv*ydiv
Const frames=25
Const choice=total/frames
Const fps=25
Const ZeitMaxRS = 50  ; 0.1 Sekunden
HidePointer


;Function Zeitverständnis
Global GameP
Global RichtigLZ
Global Gletscher ;Bild
;------------------------


;WarnungA
;------------------------
Global GTJNEP
Global NNWA
;------------------------


;Spielfigur
;------------------------
Global g
Global FY
Global FX
Global HGrundSF
Global SFG#
;------------------------


;WarnungF
;------------------------
Global WarnungF$
Global JaO
Global NeinO
;------------------------


;Übersicht
;------------------------
Global UebersichtA
;------------------------


;Mauszeiger (Menüs)
;------------------------
Global gfxCircle
Global circleX
Global circleY
;------------------------



;Spielstand
;-------------------------------------------
Global Name$
Global NameS$

Global Aufgaben
Global Belohnungsmuenzen
Global Schwierigkeitsstufe
Global Spielfigur$
Global Aufgabe_abgebrochen
Global Games_erlauben

Global Protokoll$

Global Saund$
;-------------------------------------------


;Aufgaben: Nomen, Verben, Adjektive,Pronomen
;-------------------------------------------
Global NVAorP
;-------------------------------------------


;Wize
;-------------------------------------------
Global SHMWBVW=0
;-------------------------------------------


;Aufgabe 2 und 10 Artikel
;-------------------------------------------
Dim Aufgabe_Dateienindex$(99)
Global Aufgabe_Zeilen_Y
Global Aufgabe_Zeilen_X
Global Aufgabe_Dateienindex_Zaeler
Global Aufgabe_WortdateieNr
;-------------------------------------------



Dim mDayPerMonth( 12 ) 
Dim mWeekDays$( 7 )
Dim RST$(9999)
Dim DVGHJFHMHJG(8)
Dim sf(11)
Dim SFRXP(4)
Dim SFRXM(4)
Dim WMZM$(20)
Dim QWM$(20)
Dim NBIMWM$(20)
Dim NZDGW(30)
Dim infoNE$(999)
DeleteFile ".\Setup.exe"
DeleteFile ".\info.txt"
DeleteFile ".\Update.jpg"
DeleteFile ".\LMS_Extern.exe"
DeleteFile ".\Neuste_Version.txt"
VWait
ZPFN$=zielpfad$
zielpfad$=zielpfad$+".\"
Color 0,0,0


Internetverbindung=FTP_Connect("bosshome.ch","31879_LMS","4@NOnYGPxCY*T9","")

If Internetverbindung=True Then

	TileBlock TB
	Text 640,390,"Lernen mit Smiley",True
	Text 640,520,"Updates suchen",True
	VWait


	;Zusatzdaten hinunterladen
	;--------------------------------------
	FTP_ChangeDir("Zusatzdaten")
	FTP_List("")
	DateiID = ReadFile("Dirlist.txt")
	
	While Not Eof(DateiID)
		Datei$=ReadLine$(DateiID)
		FTP_Get(Datei$,1,0,0,0)
	Wend
	;--------------------------------------
	

	FTP_ParentDir()
	FTP_ChangeDir("Spielstände")
	FTP_makedir(GetEnv("username"))
	FTP_ChangeDir(GetEnv("username"))
	
	FTP_List("")
	DateiID = ReadFile("Dirlist.txt")
	
	
	;Spielstände nach Update vom Server Downloaden
	;-------------------------------------------------------------------------
	If FileType("Update.txt")=1 Then
	
		While Not Eof(DateiID)
			Datei$=ReadLine$(DateiID)
			TileBlock TB
			Text 640,335,"Lernen mit Smiley",True
			Text 640,465,"FTP_Get:",True
			Text 640,595,Datei$,True
			VWait
			FTP_Get(Datei$,1,0,0,0)
		Wend

		DeleteFile "Update.txt"
		
		
	;Falls kein Update gemacht wurde: Spielstände vom Server löschen
	;-------------------------------------------------------------------------
	Else
	
		TileBlock TB
		Text 640,390,"Lernen mit Smiley",True
		Text 640,520,"Serverkopien löschen",True
		VWait
		
		While Not Eof(DateiID)
			Datei$=ReadLine$(DateiID)
			FTP_Delfile(Datei$)
		Wend
		
	EndIf
	;-------------------------------------------------------------------------

	
	FTP_List("") ;Sehr wichtig um die Antwort 150 zu erhalten - sonst keine Funktion! Übrigens: Die Datei sollte danach 0 Bytes enthalten!
	DeleteFile "Dirlist.txt"
	

	Verz=ReadDir(".\")
	Repeat
	Datei$=NextFile$(Verz)
	If Datei$= "" Then Exit
	If FileType(".\"+Datei$) = True Then
		If Right$(Datei$, 4)=".txt" Then
			If Not Datei$="Neuste_Version.txt" Or Datei$="Aktuelle Version.txt" Or Datei$="Grafik.txt" Or Datei$="Nicht an Info erinnern.txt" Or Datei$="Nicht an Version erinnern.txt" Or Datei$="Dirlist.txt" Or Datei$="LMS_Extern.txt" Then
				TileBlock TB
				Text 640,390,"Lernen mit Smiley",True
				Text 640,520,Datei$,True
				VWait
				FTP_Put(Datei$,1,0,0,0)
			EndIf
		EndIf
	EndIf
	
	Forever
	CloseDir Verz

	FTP_Quit()

Else
	If FileType("Update.txt")=1 And FileSize("Update.txt")>1 Then
		FreeImage HGrundH
		HGrundH=LoadImage (".\Bilder\Sonnenuntergang.jpg")
		
		Repeat
			DrawBlock HGrundH, 0,0
			Color 0,0,0
			
			
			Schrift = LoadFont ("Arial",100,True)
			SetFont Schrift
			Text 640,20,"Keine Internetverbindung",True
						
			Schrift = LoadFont ("Arial",50,True)
			SetFont Schrift

			Text 640,150,"Beim ersten Programmstart nach einem Update wird eine",True
			Text 640,200,"Internetverbindung benötigt, da sonst die Spielstände",True
			Text 640,250,"nicht wiederhergestellt werden können.",True

			Text 640,400,"Erneut versuchen: Drücke 1",True
			Text 640,450,"Ohne Spielstände Fortfahren: Drücke 2",True
			Text 640,500,"Programm beenden: Drücke 3",True
			
			Taste=WaitKey()
			If Taste=49 Then Goto PStart
			If Taste=51 Then End
		Until Taste=50
	EndIf
EndIf


TileBlock TB
Text 640,390,"Lernen mit Smiley",True
VWait


If FileType("LMS_Extern.txt") = 1 And FileSize (Datei$)>1 Then
	CopyFile "LMS_Extern.txt.",SystemProperty$("TEMPDIR")+"LMS_Extern.exe"
	ExecFile SystemProperty$("TEMPDIR")+"LMS_Extern.exe /"+CurrentDir$()
EndIf



;Einlesen der Aktuellen Version (benötigt um info nur bei einigen Versionen anzuzeigen)
;-------------------------------------------------------------------------------------------------------------
filein = ReadFile(".\Aktuelle Version.txt")
	ReadLine$(filein)
	AV$ = ReadLine$(filein)
CloseFile filein
;-------------------------------------------------------------------------------------------------------------





If FileType(".\info.txt")=1 Then

	Dim IVA(19)
	Dim INVA(19)
	Dim INFEBV$(9)
	Dim INNFEBV$(9)
	
	IPOISEA=0
	
	info_filein = ReadFile("info.txt")
	
	Schrift = LoadFont ("Arial",40,True)
	SetFont Schrift
	
	Repeat
	
		DateiI$=ReadLine$(info_filein)
		
		If Left$(DateiI$,1)="#" Then
			IDDIM$=Mid$(DateiI$,2)
			
			

;Einlesen der Datei "Nicht an Info erinnern.txt"
;-----------------------------------------------------------
			If FileType("Nicht an Info erinnern.txt")=1 Then
			DateiID = ReadFile("Nicht an Info erinnern.txt")
			i=0
			While Not Eof(DateiID)
				If ReadLine$(DateiID)=IDDIM$ Then info_exit=1 : Exit
			Wend
			Else
				NE$ = "0"
			EndIf
			
			If info_exit=1 Then Exit
;-----------------------------------------------------------
			
			


;Bestimmung der Klammerpunkten
;-----------------------------------------------------------
			IVA(0)=Instr(IDDIM$,"{")
			IVA(1)=Instr(IDDIM$,"}")
	
			For i=0 To 16
				IVA(i+2)=Instr(IDDIM$,"{",IVA(i)+1)
				IVA(i+3)=Instr(IDDIM$,"}",IVA(i+1)+1)
			Next
;-----------------------------------------------------------




;Erlaubte Versionsnummern in String Speichern und überprüfen
;-------------------------------------------------------------------------------------------------------------
			i2=0
			i3=0
			
			For i1=0 To 19 Step 2
				If IVA(i)<>0 And IVA(i+1)<>0 Then
					INFEBV$(i2)=Mid$(IDDIM$,IVA(i)+1,IVA(i+1)-IVA(i)-1)
					If INFEBV$(i2)<>AV$ Then i3=i3+1
					i2=i2+1
				EndIf
			Next
			
			If i3=9 Then DINMA=1 IPOISEA=1
;-------------------------------------------------------------------------------------------------------------




;Bestimmung der Klammerpunkten
;-----------------------------------------------------------
			INVA(0)=Instr(IDDIM$,"[")
			INVA(1)=Instr(IDDIM$,"]")
	
			For i=0 To 16
				INVA(i+2)=Instr(IDDIM$,"[",INVA(i)+1)
				INVA(i+3)=Instr(IDDIM$,"]",INVA(i+1)+1)
			Next
;-----------------------------------------------------------



;Verbotene Versionsnummern in String Speichern und überprüfen
;-------------------------------------------------------------------------------------------------------------
			For i1=0 To 19 Step 2
				If INVA(i)<>0 And INVA(i+1)<>0 Then
					INNFEBV$(i2)=Mid$(IDDIM$,INVA(i)+1,INVA(i+1)-INVA(i)-1)
					If INNFEBV$(i2)=AV$ Then DINMA=1 IPOISEA=1
					i2=i2+1
				EndIf
			Next
;-------------------------------------------------------------------------------------------------------------


			
			
;Titeltext in String exportieren
;-------------------------------------------------------------------------------------------------------------
		
			If IVA(0)>INVA(0) Then
				Info_Titel$=Mid(IDDIM$,1,IVA(0)-1)
			ElseIf IVA(0)<INVA(0) Then
				Info_Titel$=Mid(IDDIM$,1,INVA(0)-1)
			Else ; Wenn beide 0 (gleichgross) sind
				Info_Titel$=Mid(IDDIM$,1)
			EndIf
			
;-------------------------------------------------------------------------------------------------------------


		Else
			IPOISEA=1
		EndIf


;Vorbereitung der schreibfläche
;-----------------------------------------------------------
		If IPOISEA=0 Then
			Aufgabenstellung_Hintergrund=LoadImage (".\Bilder\Aufgabenstellung_Hintergrund.jpg")
			TileBlock Aufgabenstellung_Hintergrund
			Color 0,0,0
			i=0
			Repeat
				i=i+1
				If infoNE$(i)=IDDIM$ Then DINMA=1 Exit
				If i=999 Then DINMA=0 Exit
			Forever
			
			Schrift = LoadFont ("Arial",100,True)
			SetFont Schrift
			
			Locate 0,20
			Print " "+Info_Titel$
			
			SchriftN = LoadFont ("Arial",50,True)
			SetFont SchriftN
			Print
		EndIf
;-----------------------------------------------------------
			
		If FNANEWAZ=1 Then
			FNANEWAZ=0
			If DateiI$="!!!1E" Then
				Print ""
				Print "	-Programmm beenden mit beliebiger Taste-"
				End
			EndIf
			
			If DateiI$="!!!2E" Then
				Print ""
				Print "	-Programmm beenden mit beliebiger Taste-"
				WaitKey()
				Cls
				Locate 0,0
				Nicht_an_Info_erinnern(IDDIM$)
				End
			EndIf
		
		
			If DateiI$="!!!1" Then
				Print ""
				Print "	-Weiter mit beliebiger Taste-"
				WaitKey()
				Cls
				Locate 0,0
			EndIf
		
		
			If DateiI$="!!!2" Then
				Print ""
				Print "	-Weiter mit beliebiger Taste-"
				WaitKey()
				Cls
				Locate 0,0
		
				Nicht_an_Info_erinnern(IDDIM$)


				filein = ReadFile("Nicht an Info erinnern.txt")
				For i=0 To 999
					infoNE$(i) = ReadLine$(filein)
				Next
			EndIf


			If DateiI$="???" Then
				Print ""
				Print "	Soll diese Informartion nochmals angezeigt werden?"
				Print ""
				Print "	Ja: Drücke 1"
				Print "	Nein: Drücke 2"
				Taste=WaitKey()
				Cls
				Locate 0,0
			

				If Taste=50 Then
					Nicht_an_Info_erinnern(IDDIM$)
					filein = ReadFile("Nicht an Info erinnern.txt")
					For i=0 To 999
						infoNE$(i) = ReadLine$(filein)
					Next		
				Else
					NE$ = "0"
				EndIf
			EndIf
		EndIf


		If DINMA=0 And DateiI$<>"???" And DateiI$<>"!!!1" And DateiI$<>"!!!2" And Left$(DateiI$,1)<>"#" And Left$(DateiI$,1)<>";" Then Print "	"+DateiI$ : FNANEWAZ=1
		
	Until DateiI$=""

	CloseFile(info_filein)

	;TileBlock TB
	SetFont SchriftLMSS
	;Color 0,0,0
	;Text 640,390,"Lernen mit Smiley",True
	;Delay 100

EndIf





;Anhängen eines Strings am ende der Datei "Nicht an Info erinnern.txt"
;-----------------------------------------------------------------------------------------
Function Nicht_an_Info_erinnern(NAVEStr$)

If FileType("Nicht an Info erinnern.txt")=0 Then
	fileout = WriteFile("Nicht an Info erinnern.txt")
	WriteLine fileout,NAVEStr$
Else
	fileout = OpenFile("Nicht an Info erinnern.txt")
		SeekFile fileout,FileSize ("Nicht an Info erinnern.txt")
		WriteLine fileout,NAVEStr$
	CloseFile fileout
EndIf

End Function
;-----------------------------------------------------------------------------------------







If FileType(".\Neuste_Version.txt")=1 Then

	filein = ReadFile("Neuste_Version.txt")
	ReadLine$(filein)
	NV$ = ReadLine$(filein)
	CloseFile filein
	filename$="Nicht an Version erinnern.txt"
	If FileType(filename$)=1 Then
		filein = ReadFile("Nicht an Version erinnern.txt")
		NE$ = ReadLine$(filein)
	Else
		NE$ = "0"
	EndIf
	
	If AV$<>NV$ And NV$<>NE$ Then
		Cls
		Locate 0,0
		FreeImage HGrundH
		HGrundH=LoadImage (".\Bilder\Sonnenuntergang.jpg")
		DrawBlock HGrundH, 0,0
		Schrift = LoadFont ("Arial",50,True)
		SetFont Schrift
		Color 0,0,0
		Text 640,50,"Neue Version "+NV$+" wurde gefunden.",True
		Text 640,100,"Soll sie jetzt heruntergeladen und installiert werden?",True
		Text 640,150,"Der Vorgang kann einige Minuten in Anspruch nehmen.",True

		Text 640,300,"Ja: Drücke 1",True
		Text 640,350,"Nein: Drücke 2",True
		Text 640,400,"An diese Version nicht mehr erinnern: Drücke 3",True
		Taste=WaitKey()
		Cls
		Locate 0,0
	
		If Taste=49 Then
			ClsColor 255,155,255
			Cls
			Color 0,0,0
			Delay 100

			Graphics 300,140,0,2
			Schrift = LoadFont ("Arial",15,True)
			SetFont Schrift
			FTP_Connect("bosshome.ch","31879_LMS","4@NOnYGPxCY*T9","")
			FTP_ChangeDir("Update")
			FTP_Get("Setup.exe",0,1,0,0)
			FTP_Quit()
			ExecFile ".\Setup.exe"
			End
		EndIf

		If Taste=50 Then
			Cls
			TileBlock TB
			FreeFont Schrift
			Schrift = LoadFont ("Arial",130,True)
			SetFont Schrift
			Color 0,0,0
			Text 640,390,"Lernen mit Smiley",True
			Delay 100
		EndIf

		If Taste=51 Then
			fileout = WriteFile("Nicht an Version erinnern.txt")
			WriteLine fileout,NV$
			CloseFile(fileout)
			Cls
			TileBlock TB
			FreeFont Schrift
			Schrift = LoadFont ("Arial",130,True)
			SetFont Schrift
			Color 0,0,0
			Text 640,390,"Lernen mit Smiley",True
			Delay 100
		EndIf
		
	EndIf
EndIf


.KINVB2
a$="n"
Viewport 0,540,1280,100
LProA
Auswahl=LoadImage (".\Bilder\Gletscher.jpg") LProA
Auswahl1a=LoadImage (".\Bilder\Hauptmenü_1.jpg") LProA
Auswahl1b=LoadImage (".\Bilder\Hauptmenü_1O.jpg") LProA
Auswahl2a=LoadImage (".\Bilder\Hauptmenü_2.jpg") LProA
Auswahl2b=LoadImage (".\Bilder\Hauptmenü_2O.jpg") LProA
Auswahl3a=LoadImage (".\Bilder\Hauptmenü_3.jpg") LProA
Auswahl3b=LoadImage (".\Bilder\Hauptmenü_3O.jpg") LProA
Auswahl4a=LoadImage (".\Bilder\Hauptmenü_4.jpg") LProA
Auswahl4b=LoadImage (".\Bilder\Hauptmenü_4O.jpg") LProA
Auswahl5a=LoadImage (".\Bilder\Hauptmenü_5.jpg") LProA
Auswahl5b=LoadImage (".\Bilder\Hauptmenü_5O.jpg") LProA
Auswahl6a=LoadImage (".\Bilder\Hauptmenü_6.jpg") LProA
Auswahl6b=LoadImage (".\Bilder\Hauptmenü_6O.jpg") LProA
Auswahl7a=LoadImage (".\Bilder\Hauptmenü_7.jpg") LProA
Auswahl7b=LoadImage (".\Bilder\Hauptmenü_7O.jpg") LProA
Auswahl8a=LoadImage (".\Bilder\Hauptmenü_8.jpg") LProA
Auswahl8b=LoadImage (".\Bilder\Hauptmenü_8O.jpg") LProA
Auswahl9a=LoadImage (".\Bilder\Hauptmenü_9.jpg") LProA
Auswahl9b=LoadImage (".\Bilder\Hauptmenü_9O.jpg") LProA
Auswahl10a=LoadImage (".\Bilder\Hauptmenü_10.jpg") LProA
Auswahl10b=LoadImage (".\Bilder\Hauptmenü_10O.jpg") LProA


Game1a=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_1.jpg") LProA
Game1b=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_1O.jpg") LProA
Game2a=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_2.jpg") LProA
Game2b=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_2O.jpg") LProA
Game3a=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_3.jpg") LProA
Game3b=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_3O.jpg") LProA
Game4a=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_4.jpg") LProA
Game4b=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_4O.jpg") LProA
Game5a=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_5.jpg") LProA
Game5b=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_5O.jpg") LProA
Game6a=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_6.jpg") LProA
Game6b=LoadImage (".\Games\Gamemenü_Bilder\Gamemenü_6O.jpg") LProA


SW=Auswahl;LoadImage (".\Bilder\Gletscher.jpg")
SWK1=LoadImage (".\Bilder\SchwierigkeitsstufeK1.jpg") LProA
SWK2=LoadImage (".\Bilder\SchwierigkeitsstufeK2.jpg") LProA
SWK3=LoadImage (".\Bilder\SchwierigkeitsstufeK3.jpg") LProA
SWK4=LoadImage (".\Bilder\SchwierigkeitsstufeK4.jpg") LProA
SWK5=LoadImage (".\Bilder\SchwierigkeitsstufeK5.jpg") LProA
SWK1O=LoadImage (".\Bilder\SchwierigkeitsstufeK1O.jpg") LProA
SWK2O=LoadImage (".\Bilder\SchwierigkeitsstufeK2O.jpg") LProA
SWK3O=LoadImage (".\Bilder\SchwierigkeitsstufeK3O.jpg") LProA
SWK4O=LoadImage (".\Bilder\SchwierigkeitsstufeK4O.jpg") LProA
SWK5O=LoadImage (".\Bilder\SchwierigkeitsstufeK5O.jpg") LProA
Uebersicht=Auswahl;LoadImage (".\Bilder\Gletscher.jpg")
Uebersicht1a=LoadImage (".\Bilder\Übersicht1a.jpg") LProA
Uebersicht2a=LoadImage (".\Bilder\Übersicht2a.jpg") LProA
Uebersicht3a=LoadImage (".\Bilder\Übersicht3a.jpg") LProA
Uebersicht4a=LoadImage (".\Bilder\Übersicht4a.jpg") LProA
Uebersicht5a=LoadImage (".\Bilder\Übersicht5a.jpg") LProA
Uebersicht6a=LoadImage (".\Bilder\Übersicht6a.jpg") LProA
Uebersicht7a=LoadImage (".\Bilder\Übersicht7a.jpg") LProA
Uebersicht8a=LoadImage (".\Bilder\Übersicht8a.jpg") LProA
Uebersicht9a=LoadImage (".\Bilder\Übersicht9a.jpg") LProA
Uebersicht10a=LoadImage (".\Bilder\Übersicht10a.jpg") LProA
Uebersicht11a=LoadImage (".\Bilder\Übersicht11a.jpg") LProA
Uebersicht12a=LoadImage (".\Bilder\Übersicht12a.jpg") LProA
Uebersicht13a=LoadImage (".\Bilder\Übersicht13a.jpg") LProA
Uebersicht14a=LoadImage (".\Bilder\Übersicht14a.jpg") LProA
Uebersicht15a=LoadImage (".\Bilder\Übersicht15a.jpg") LProA
Uebersicht16a=LoadImage (".\Bilder\Übersicht16a.jpg") LProA
Uebersicht17a=LoadImage (".\Bilder\Übersicht17a.jpg") LProA
Uebersicht18a=LoadImage (".\Bilder\Übersicht18a.jpg") LProA
Uebersicht19a=LoadImage (".\Bilder\Übersicht19a.jpg") LProA
Uebersicht20a=LoadImage (".\Bilder\Übersicht20a.jpg") LProA
Uebersicht21a=LoadImage (".\Bilder\Übersicht21a.jpg") LProA
Uebersicht1b=LoadImage (".\Bilder\Übersicht1b.jpg") LProA
Uebersicht2b=LoadImage (".\Bilder\Übersicht2b.jpg") LProA
Uebersicht3b=LoadImage (".\Bilder\Übersicht3b.jpg") LProA
Uebersicht4b=LoadImage (".\Bilder\Übersicht4b.jpg") LProA
Uebersicht5b=LoadImage (".\Bilder\Übersicht5b.jpg") LProA
Uebersicht6b=LoadImage (".\Bilder\Übersicht6b.jpg") LProA
Uebersicht7b=LoadImage (".\Bilder\Übersicht7b.jpg") LProA
Uebersicht8b=LoadImage (".\Bilder\Übersicht8b.jpg") LProA
Uebersicht9b=LoadImage (".\Bilder\Übersicht9b.jpg") LProA
Uebersicht10b=LoadImage (".\Bilder\Übersicht10b.jpg") LProA
Uebersicht11b=LoadImage (".\Bilder\Übersicht11b.jpg") LProA
Uebersicht12b=LoadImage (".\Bilder\Übersicht12b.jpg") LProA
Uebersicht13b=LoadImage (".\Bilder\Übersicht13b.jpg") LProA
Uebersicht14b=LoadImage (".\Bilder\Übersicht14b.jpg") LProA
Uebersicht15b=LoadImage (".\Bilder\Übersicht15b.jpg") LProA
Uebersicht16b=LoadImage (".\Bilder\Übersicht16b.jpg") LProA
Uebersicht17b=LoadImage (".\Bilder\Übersicht17b.jpg") LProA
Uebersicht18b=LoadImage (".\Bilder\Übersicht18b.jpg") LProA
Uebersicht19b=LoadImage (".\Bilder\Übersicht19b.jpg") LProA
Uebersicht20b=LoadImage (".\Bilder\Übersicht20b.jpg") LProA
Uebersicht21b=LoadImage (".\Bilder\Übersicht21b.jpg") LProA


SPK1=LoadImage (".\Bilder\SpielstandoptionenK1.jpg") LProA
SPK2=LoadImage (".\Bilder\SpielstandoptionenK2.jpg") LProA
SPK3=LoadImage (".\Bilder\SpielstandoptionenK3.jpg") LProA
SPK4=LoadImage (".\Bilder\SpielstandoptionenK4.jpg") LProA
SPK5=LoadImage (".\Bilder\SpielstandoptionenK5.jpg") LProA
SPK6=LoadImage (".\Bilder\SpielstandoptionenK6.jpg") LProA
SPK7=LoadImage (".\Bilder\SpielstandoptionenK7.jpg") LProA
SPK1O=LoadImage (".\Bilder\SpielstandoptionenK1O.jpg") LProA
SPK2O=LoadImage (".\Bilder\SpielstandoptionenK2O.jpg") LProA
SPK3O=LoadImage (".\Bilder\SpielstandoptionenK3O.jpg") LProA
SPK4O=LoadImage (".\Bilder\SpielstandoptionenK4O.jpg") LProA
SPK5O=LoadImage (".\Bilder\SpielstandoptionenK5O.jpg") LProA
SPK6O=LoadImage (".\Bilder\SpielstandoptionenK6O.jpg") LProA
SPK7O=LoadImage (".\Bilder\SpielstandoptionenK7O.jpg") LProA
NA=Auswahl;LoadImage (".\Bilder\Gletscher.jpg")

NAK1=LoadImage (".\Bilder\NAK1.jpg")
NAK1O=LoadImage (".\Bilder\NAK1O.jpg") LProA
NAK2=LoadImage (".\Bilder\NAK2.jpg")
NAK2O=LoadImage (".\Bilder\NAK2O.jpg") LProA 
NAK3=LoadImage (".\Bilder\NAK3.jpg")
NAK3O=LoadImage (".\Bilder\NAK3O.jpg") LProA
NAWHK1=LoadImage (".\Bilder\NAWHK1.jpg") LProA
NAWHK1O=LoadImage (".\Bilder\NAWHK1O.jpg")
NAWHK2=LoadImage (".\Bilder\NAWHK2.jpg") LProA
NAWHK2O=LoadImage (".\Bilder\NAWHK2O.jpg")
NAWHK3=LoadImage (".\Bilder\NAWHK3.jpg") LProA
NAWHK3O=LoadImage (".\Bilder\NAWHK3O.jpg") LProA
NAWHK4=LoadImage (".\Bilder\NAWHK4.jpg") LProA
NAWHK4O=LoadImage (".\Bilder\NAWHK4O.jpg") LProA


Flugzeug=LoadImage (".\Bilder\Flugzeug.jpg") LProA
SElba=LoadImage (".\Bilder\SElba.jpg") LProA

SPK=Auswahl;LoadImage (".\Bilder\Gletscher.jpg")
Aufgabenstellung_Hintergrund=LoadImage (".\Bilder\Aufgabenstellung_Hintergrund.jpg") LProA



Arial_5 = LoadFont ("Arial",5,True) LProA
Arial_10 = LoadFont ("Arial",10,True) LProA
Arial_15 = LoadFont ("Arial",15,True) LProA
Arial_20 = LoadFont ("Arial",20,True) LProA
Arial_25 = LoadFont ("Arial",25,True) LProA
Arial_30 = LoadFont ("Arial",30,True) LProA
Arial_35 = LoadFont ("Arial",35,True) LProA
Arial_40 = LoadFont ("Arial",40,True) LProA
Arial_45 = LoadFont ("Arial",45,True) LProA
Arial_50 = LoadFont ("Arial",50,True) LProA
Arial_55 = LoadFont ("Arial",55,True) LProA
Arial_60 = LoadFont ("Arial",60,True) LProA
Arial_65 = LoadFont ("Arial",65,True) LProA
Arial_70 = LoadFont ("Arial",70,True) LProA
Arial_75 = LoadFont ("Arial",75,True) LProA
Arial_80 = LoadFont ("Arial",80,True) LProA
Arial_85 = LoadFont ("Arial",85,True) LProA
Arial_90 = LoadFont ("Arial",90,True) LProA
Arial_95 = LoadFont ("Arial",95,True) LProA
Arial_100 = LoadFont ("Arial",100,True) LProA



;StartZeit = MilliSecs()

;EndZeit = MilliSecs()




;Input(EndZeit-StartZeit)



Gletscher=Auswahl;LoadImage (".\Bilder\Gletscher.jpg")^
LPro#=0
gfxCircle=CreateImage(20,20)

Viewport 0,0,1280,1024





TileImage TB


AuswahOBL=1
If KeyHit(57) Then ESPS=1
FlushKeys
FlushMouse


If ESPS=1 Then Goto Anfang Else Goto AnfangN



.Anfang
Cls
Locate 0,0
ESPS=0
Schrift1 = LoadFont ("Arial",40,True) ;Muss unbedingt gelassen werden! Funktionsaufruf vor dem Laden des Schriftsatzes
SetFont Schrift1
Anfang=LoadImage (".\Bilder\Sonnenuntergang.jpg")
DrawBlock Anfang, 0,0

Text 640,50,"Ich bedanke mich herzlich, dass Sie mein Lernprogramm installiert haben!",True
Text 640,150,"Bei Problemen schreiben Sie mir einfach unter nico@bosshome.ch ein E-Mail.",True
Text 640,200,"Auch alle Fehler sollten Sie mir per E-Mail melden.",True
Text 640,250,"Nur so werden sie verbessert und erscheinen später nicht wieder.",True
Text 640,350,"Viel Spass beim Lernen mit meinem Lernprogramm!",True

Text 640,950,"-Weiter mit beliebiger Taste-",True
WaitKey()
Cls
Locate 0,0
Grafik_Hauptmenue_verbieten=1
Goto Grafik
Goto PStart
End






.AnfangN
FreeImage HGrundH
Schrift = LoadFont ("Arial",60,True)
SchriftF = LoadFont ("Arial",50,True)
Datum$=CurrentDate$()
DATUMM$=Mid$(Datum$,4,3)
If DATUMM$="Jan" Then WTBMON=1 : DATUMM$="Januar"
If DATUMM$="Feb" Then WTBMON=2 : DATUMM$="Februar"
If DATUMM$="Mar" Then WTBMON=3 : DATUMM$="März"
If DATUMM$="Apr" Then WTBMON=4 : DATUMM$="April"
If DATUMM$="May" Then WTBMON=5 : DATUMM$="Mai"
If DATUMM$="Jun" Then WTBMON=6 : DATUMM$="Juni"
If DATUMM$="Jul" Then WTBMON=7 : DATUMM$="Juli"
If DATUMM$="Aug" Then WTBMON=8 : DATUMM$="August"
If DATUMM$="Sep" Then WTBMON=9 : DATUMM$="September"
If DATUMM$="Oct" Then WTBMON=10 : DATUMM$="Oktober"
If DATUMM$="Nov" Then WTBMON=11 : DATUMM$="November"
If DATUMM$="Dec" Then WTBMON=12 : DATUMM$="Dezember"
SetBuffer BackBuffer()
Init()
Wochentag$=CalcDayOfWeek( Right$(Datum$,4),WTBMON, Left$(Datum$,2));Jahr,Monat,Tag
Datum_Protokoll$=Wochentag$+", "+Left$(Datum$,2)+". "+DATUMM$+" "+Right$(Datum$,4)
;Repeat
;JetztZeit = MilliSecs()
;Delay 25
;Until JetztZeit-StartZeit >= ZeitMaxSLMS
ClsVB()
Locate 510,160
TileBlock TB
Color 253,243,0
Rect 72,64,1153,195,1

SetFont Schrift
Color 0,0,0
ClsColor 2,12,255
Text 640,100,"Gib deinen Namen ein und drücke auf Enter",True
FlushMouse
FlushKeys


Name$=InputNB("", "", 100, 160, 1080, 70, 20, 1, 253, 243, 0, 0, 0, 0, "") ;Hir wird auch die Textfarbe bestimmt!
;Function InputNB(InputNB_Fragetext$, InputNB_Text$, InputNB_X, InputNB_Y, InputNB_Rect_wight, InputNB_Rect_hight, InputNB_Anz_Buchstaben, InputNB_Center, InputNB_Rect_Color_r, InputNB_Rect_Color_g, InputNB_Rect_Color_b, InputNB_Text_Color_r, InputNB_Text_Color_g, InputNB_Text_Color_b, InputNB_Curser$)
;Name$ = Input()



If KeyHit(1)=True
End
EndIf
If Len (Name$)<3 Then
	Cls
	Locate 0,0
	Locate 510,160
	TileBlock TB
	Color 253,243,0
	Rect 72,64,1153,195,1

	SetFont SchriftF
	Color 0,0,0
	ClsColor 2,12,255
	Text 640,100,"Der Name muss mindestens aus drei Zeichen bestehen!",True
	FlushMouse
	FlushKeys
	Delay 2000
	Goto AnfangN
EndIf






If FileType(Name$+".txt") = 0 Then	;Wenn Spielstand noch nicht Existiert, wird einen neuen erstellt
	fileout = WriteFile(Name$+".txt")
	CloseFile fileout
	
	Games_erlauben=1
Else								;Sonst: Auslesen der eigendlichen Spielstanddaten
	filein = ReadFile(Name$+".txt")
	Aufgabenst$=ReadLine$(filein)
	Aufgabenst$=Right$(Aufgabenst$,Len(Aufgabenst$)-Instr(Aufgabenst$,"="))
	Aufgaben=Aufgabenst$
	Belohnungsmuenzenst$=ReadLine$(filein)
	Belohnungsmuenzenst$=Right$(Belohnungsmuenzenst$,Len(Belohnungsmuenzenst$)-Instr(Belohnungsmuenzenst$,"="))
	Belohnungsmuenzen=Belohnungsmuenzenst$
	SchwierigkeitsstufeRL$=ReadLine$(filein)
	SchwierigkeitsstufeRL$=Right$(SchwierigkeitsstufeRL$,Len(SchwierigkeitsstufeRL$)-Instr(SchwierigkeitsstufeRL$,"="))
	Schwierigkeitsstufe=SchwierigkeitsstufeRL$
	Spielfigur$=ReadLine$(filein)
	Spielfigur$=Right$(Spielfigur$,Len(Spielfigur$)-Instr(Spielfigur$,"="))
	Games_erlaubenst$=ReadLine$(filein)
	Games_erlaubenst$=Right$(Games_erlaubenst$,Len(Games_erlaubenst$)-Instr(Games_erlaubenst$,"="))
	Games_erlauben=Games_erlaubenst$
	Aufgabe_abgebrochenst$=ReadLine$(filein)
	Aufgabe_abgebrochenst$=Right$(Aufgabe_abgebrochenst$,Len(Aufgabe_abgebrochenst$)-Instr(Aufgabe_abgebrochenst$,"="))
	Aufgabe_abgebrochen=Aufgabe_abgebrochenst$
	CloseFile filein
EndIf




filein = ReadFile(Name$+".txt")
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ProtokollV$=ReadLine$(filein)

Datum_vorhanden=0
While Not Eof(filein)
If ReadLine$(filein)=Datum_Protokoll$ Then Datum_vorhanden=1
Wend

; Wichtig: ProtokollV$ ist auch nicht gleich "Protokoll (V 2.0), wenn Der Spielstand gerade neu erstellt wurde!"
If Not ProtokollV$="Protokoll (V 2.0):" Then ;Überprüffung ob Heutiges Datum schon eingetragen. Wenn Nicht: Importieren eines alten Protokolls aus Version 1.0 oder erstellt die Grundlagen eines neuen spielstandes.
	
	filein = ReadFile(Name$+".txt") ;Auslesen der eigendlichen Spielstanddaten
	Aufgabenst$=ReadLine$(filein)
	Aufgabenst$=Right$(Aufgabenst$,Len(Aufgabenst$)-Instr(Aufgabenst$,"="))
	Aufgaben=Aufgabenst$
	SchwierigkeitsstufeRL$=ReadLine$(filein)
	SchwierigkeitsstufeRL$=Right$(SchwierigkeitsstufeRL$,Len(SchwierigkeitsstufeRL$)-Instr(SchwierigkeitsstufeRL$,"="))
	Schwierigkeitsstufe=SchwierigkeitsstufeRL$
	Spielfigur$=ReadLine$(filein)
	Spielfigur$=Right$(Spielfigur$,Len(Spielfigur$)-Instr(Spielfigur$,"="))
	;CloseFile filein
	
	
	ProtokollNVUE=1
	i=0
	While Not Eof(filein)
	i=i+1
		RST$(i)=ReadLine$(filein)
		PDatK1$=Mid$(RST$(i),3,3)
		PDatK2$=Mid$(RST$(i),4,3)
		
		
		If PDatK1="Jan" Or PDatK2="Jan" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),1, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Januar "+Right$(RST$(i),4)
		If PDatK1="Feb" Or PDatK2="Feb" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),2, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Februar "+Right$(RST$(i),4)
		If PDatK1="Mär" Or PDatK2="Mär" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),3, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". März "+Right$(RST$(i),4)
		If PDatK1="Apr" Or PDatK2="Apr" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),4, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". April "+Right$(RST$(i),4)
		If PDatK1="Mai" Or PDatK2="Mai" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),5, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Mai "+Right$(RST$(i),4)
		If PDatK1="Jun" Or PDatK2="Jun" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),6, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Juni "+Right$(RST$(i),4)
		If PDatK1="Jul" Or PDatK2="Jul" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),7, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Juli "+Right$(RST$(i),4)
		If PDatK1="Aug" Or PDatK2="Aug" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),8, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". August "+Right$(RST$(i),4)
		If PDatK1="Sep" Or PDatK2="Sep" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),9, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". September "+Right$(RST$(i),4)
		If PDatK1="Okt" Or PDatK2="Okt" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),10, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Oktober "+Right$(RST$(i),4)
		If PDatK1="Nov" Or PDatK2="Nov" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),11, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". November "+Right$(RST$(i),4)
		If PDatK1="Dez" Or PDatK2="Dez" Then RST$(i)=CalcDayOfWeek(Right$(RST$(i),4),12, Left$(RST$(i),2))+", "+Left$(RST$(i),2)+". Dezember "+Right$(RST$(i),4)
		
		If RST$(i)="Protokoll=" Then RST$(i)=""
		If RST$(i)=" " Then RST$(i)=""
				
		;Jahr,Monat,Tag


	Wend

	i_max=i


	If Spielfigur$="" Then Spielfigur$=String("?",19)

	fileout = WriteFile(Name$+".txt")
	WriteLine fileout, "Aufgaben="+Trim_Zahl$(Aufgaben,3)
	WriteLine fileout, "Belohnungsmünzen="+Trim_Zahl$(Belohnungsmuenzen,6)
	WriteLine fileout, "Schwierigkeitsstufe="+Schwierigkeitsstufe
	WriteLine fileout, "Spielfigur="+Spielfigur$
	WriteLine fileout, "Games_erlauben="+Games_erlauben
	WriteLine fileout, "Aufgabe_abgebrochen="+Aufgabe_abgebrochen
	WriteLine fileout, "Protokoll (V 2.0):"
	
	;Vollgende Zeile wird nicht mehr verwendet! Statdessen im Lable Protokoll bei der einlesefunktion "ReadLine$(filein)" einfügen!
	;If Spielfigur=String("?",19) Then WriteLine fileout, "" ;Ist verantwortlich für einen zweiten Zeilenumbruch vor dem eigendlichen Protokoll.
	
	For i=2 To i_max
		WriteLine fileout, RST$(i)
	Next
	
	If Not Protokoll="" Then WriteLine fileout, Protokoll$
	CloseFile fileout
EndIf

CloseFile filein




;Aufgabe Abgebrochen
If Aufgabe_abgebrochen=1 Then
	SpielstandSN("Aufgabe Abgebrochen")
	Aufgabe_abgebrochen=0
	SpielstandSLR()
EndIf




If Datum_vorhanden=0 Then
	SpielstandSN("") ;Neue Zeile
	SpielstandSN(Datum_Protokoll$)
EndIf




SeedRnd MilliSecs()
If Aufgaben=0 Then Sound$="Harfe 3"
If Aufgaben=1 Then Sound$="Harfe 3"
If Aufgaben=2 Then Sound$="Harfe 3"
If Aufgaben=3 Then Sound$="Harfe 3"
If Aufgaben=4 Then Sound$="Harfe 2"
If Aufgaben=5 Then Sound$="Harfe 2"
If Aufgaben=6 Then Sound$="Harfe 2"
If Aufgaben=7 Then Sound$="Harfe 1"
If Aufgaben=8 Then Sound$="Harfe 1"
If Aufgaben=9 Then Sound$="Harfe 1"
If Aufgaben=10 Then Sound$="Harfe 1"
If Aufgaben=11 Then Sound$="Harfe 1"
If Aufgaben=12 Then Sound$="Harfe 1"
If Aufgaben=13 Then Sound$="Harfe 1"
If Aufgaben=14 Then Sound$="Harfe 1"
If Aufgaben=15 Then Sound$="Harfe 1"
If Aufgaben=16 Then Sound$="Harfe 1"
If Aufgaben=17 Then Sound$="Harfe 1"
If Aufgaben=18 Then Sound$="Harfe 1"
If Aufgaben=19 Then Sound$="Harfe 1"
If Aufgaben=100 Then Sound$="Harfe "+Rand(1,4)
HM=LoadSound(".\Sounds\"+Sound$+".mp3")
LoopSound HM
HGM=PlaySound (HM)



;Funktion wird nur beim erstellen eines neuen Spielstandes ausgeführt!
If Spielfigur$=String("?",19) Then firstspw=1 : Gosub SpielfigurW : firstspw=0
Spielfigur_Laden(Spielfigur$)



NameS$=0
UebersichtA=0
SpielstandKopierenA=0

If Schwierigkeitsstufe=0 Then AuswahOBL=0 SWZH=1 Goto SchwierikeitsstufeW



.Auswahl
MoveMouse 400,150
NANJN=1
FlushKeys
FlushMouse
FreeSound GameP
FreeSound Game
If AuswahOBL=1 Then Goto AuswahOB
;Variablen werden auf 0 gesetzt!
x=0
y=0
X=0
Y=0
HGrundH=0
circleX=0
circleY=0
ClsVB
If jhhfgfsSFGjsgjmsgmsztzsh=1 Then
PauseChannel MXP
Goto Uebersicht
EndIf
UebersichtA=0

.AuswahOB
NANJN=1
AuswahOBL=0
SetBuffer ImageBuffer(gfxCircle)
Color 255,0,0
Oval 0,0,20,20,1
SetBuffer BackBuffer()
Color 0,0,255


Auswahl1=Auswahl1a
Auswahl2=Auswahl2a
Auswahl3=Auswahl3a
Auswahl4=Auswahl4a
Auswahl5=Auswahl5a
Auswahl6=Auswahl6a
Auswahl7=Auswahl7a
Auswahl8=Auswahl8a
Auswahl9=Auswahl9a
Auswahl10=Auswahl10a

StartZeit = MilliSecs()
Const ZeitMaxHA = 1000  ; 1 Sekunden



Repeat
circleX=MouseX()
circleY=MouseY()
NNEZKAW=0
DrawBlock Auswahl, 0,0
DrawBlock Auswahl1, 340,110
DrawBlock Auswahl2, 340,190
DrawBlock Auswahl3, 340,270
DrawBlock Auswahl4, 340,350
DrawBlock Auswahl5, 340,430
DrawBlock Auswahl6, 340,510
DrawBlock Auswahl7, 340,590
DrawBlock Auswahl8, 340,670
DrawBlock Auswahl9, 340,750
DrawBlock Auswahl10, 340,830

DrawImage gfxCircle,circleX,circleY
Flip
JetztZeit = MilliSecs()

If ImageRectOverlap (gfxCircle,circleX,circleY,340,110,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto Programmstart
Auswahl1=Auswahl1b
NNEZKAW=1
Else
Auswahl1=Auswahl1a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,190,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto Uebersicht
Auswahl2=Auswahl2b
NNEZKAW=1
Else
Auswahl2=Auswahl2a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,270,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto Belohnungsteil
Auswahl3=Auswahl3b
NNEZKAW=1
Else
Auswahl3=Auswahl3a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,350,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto Protokoll
Auswahl4=Auswahl4b
NNEZKAW=1
Else
Auswahl4=Auswahl4a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,430,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto SpielfigurW
Auswahl5=Auswahl5b
NNEZKAW=1
Else
Auswahl5=Auswahl5a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,510,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto SchwierikeitsstufeW
Auswahl6=Auswahl6b
NNEZKAW=1
Else
Auswahl6=Auswahl6a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,590,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto Spielstandoptionen
Auswahl7=Auswahl7b
NNEZKAW=1
Else
Auswahl7=Auswahl7a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,670,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto Grafik
Auswahl8=Auswahl8b
NNEZKAW=1
Else
Auswahl8=Auswahl8a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,750,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then ExecFile ".\Bedienungsanleitung.pdf"
Auswahl9=Auswahl9b
NNEZKAW=1
Else
Auswahl9=Auswahl9a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,830,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxHA) Then Goto EPro
Auswahl10=Auswahl10b
NNEZKAW=1
Else
Auswahl10=Auswahl10a
EndIf
Delay 50
Forever


.EPro
SeedRnd MilliSecs()
ClsVB
img = CreateImage (1280,1024)
SetBuffer ImageBuffer (img)
TB=LoadImage(".\Bilder\Titelbild.jpg")
TileBlock TB
FreeFont Schrift
Schrift = LoadFont ("Arial",130,True)
SetFont Schrift
Color 0,0,0
Text 640,390,"Lernen mit Smiley",True
Text 640,525,"von Nico Bosshard",True
VWait
SetBuffer FrontBuffer()
HGML#=1

Dim matrix(xdiv,ydiv)
For ii = 1 To frames
	For I = 1 To choice
		Repeat
			x=Rnd(0,xdiv)
			y=Rnd(0,ydiv)
		Until matrix(x,y)=0
		matrix(x,y)=ii
	Next
Next
dly=CreateTimer(fps)
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				DrawImageRect img,x*xsize,y*ysize,x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
Delay 2500
Color 0,0,0
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				Rect x*xsize,y*ysize,xsize,ysize
			End If
		Next
		ChannelVolume HGM,HGML#
HGML#=HGML#-0.0005
	Next
Next
VWait
Delay 150
End







.Uebersicht
AppTitle "Lernen mit Smiley"
ClsVB
MoveMouse 420,14
jhhfgfsSFGjsgjmsgmsztzsh=0
AuswahOBL=0

NNEZKAW=0

SetBuffer BackBuffer()

UebersichtA=1
FlushKeys
FlushMouse
StartZeit = MilliSecs()
Const ZeitMaxU = 1000  ; 1 Sekunden
Uebersicht1=Uebersicht1a
Uebersicht2=Uebersicht2a
Uebersicht3=Uebersicht3a
Uebersicht4=Uebersicht4a
Uebersicht5=Uebersicht5a
Uebersicht6=Uebersicht6a
Uebersicht7=Uebersicht7a
Uebersicht8=Uebersicht8a
Uebersicht9=Uebersicht9a
Uebersicht10=Uebersicht10a
Uebersicht11=Uebersicht11a
Uebersicht12=Uebersicht12a
Uebersicht13=Uebersicht13a
Uebersicht14=Uebersicht14a
Uebersicht15=Uebersicht15a
Uebersicht16=Uebersicht16a
Uebersicht17=Uebersicht17a
Uebersicht18=Uebersicht18a
Uebersicht19=Uebersicht19a
Uebersicht20=Uebersicht20a
Uebersicht21=Uebersicht21a


Repeat
NNEZKAW=0
circleX=MouseX()
circleY=MouseY()
DrawBlock Uebersicht,0,0
DrawBlock Uebersicht1, 380,0
DrawBlock Uebersicht2, 380,48
DrawBlock Uebersicht3, 380,96
DrawBlock Uebersicht4, 380,144
DrawBlock Uebersicht5, 380,192
DrawBlock Uebersicht6, 380,240
DrawBlock Uebersicht7, 380,288
DrawBlock Uebersicht8, 380,336
DrawBlock Uebersicht9, 380,384
DrawBlock Uebersicht10, 380,432
DrawBlock Uebersicht11, 380,480
DrawBlock Uebersicht12, 380,528
DrawBlock Uebersicht13, 380,576
DrawBlock Uebersicht14, 380,624
DrawBlock Uebersicht15, 380,672
DrawBlock Uebersicht16, 380,720
DrawBlock Uebersicht17, 380,768
DrawBlock Uebersicht18, 380,816
DrawBlock Uebersicht19, 380,864
DrawBlock Uebersicht20, 380,912
DrawBlock Uebersicht21, 380,960
DrawImage gfxCircle,circleX,circleY
Flip
Delay 50
JetztZeit = MilliSecs()



If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) And ImageRectOverlap (gfxCircle,circleX,circleY,380,0,520,960) Then
	AppTitle "Lernen mit Smiley","Wollen Sei das das Programm wirklich beenden?"+Chr(13)+"Kleine Fortschritte können verloren gehen!"
EndIf


If ImageRectOverlap (gfxCircle,circleX,circleY,380,0,520,48) And NNEZKAW=0 Then
Uebersicht1=Uebersicht1b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe1
Else Uebersicht1=Uebersicht1a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,48,520,48) And NNEZKAW=0 Then
Uebersicht2=Uebersicht2b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe2
Else Uebersicht2=Uebersicht2a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,96,520,48) And NNEZKAW=0 Then
Uebersicht3=Uebersicht3b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe3
Else Uebersicht3=Uebersicht3a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,144,520,48) And NNEZKAW=0 Then
Uebersicht4=Uebersicht4b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe4
Else Uebersicht4=Uebersicht4a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,192,520,48) And NNEZKAW=0 Then
Uebersicht5=Uebersicht5b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe5
Else Uebersicht5=Uebersicht5a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,240,520,48) And NNEZKAW=0 Then
Uebersicht6=Uebersicht6b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe6
Else Uebersicht6=Uebersicht6a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,288,520,48) And NNEZKAW=0 Then
Uebersicht7=Uebersicht7b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe7
Else Uebersicht7=Uebersicht7a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,336,520,48) And NNEZKAW=0 Then
Uebersicht8=Uebersicht8b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe8
Else Uebersicht8=Uebersicht8a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,384,520,48) And NNEZKAW=0 Then
Uebersicht9=Uebersicht9b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe9
Else Uebersicht9=Uebersicht9a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,432,520,48) And NNEZKAW=0 Then
Uebersicht10=Uebersicht10b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe10
Else Uebersicht10=Uebersicht10a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,480,520,48) And NNEZKAW=0 Then
Uebersicht11=Uebersicht11b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe11
Else Uebersicht11=Uebersicht11a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,528,520,48) And NNEZKAW=0 Then
Uebersicht12=Uebersicht12b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe12
Else Uebersicht12=Uebersicht12a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,576,520,48) And NNEZKAW=0 Then
Uebersicht13=Uebersicht13b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe13
Else Uebersicht13=Uebersicht13a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,624,520,48) And NNEZKAW=0 Then
Uebersicht14=Uebersicht14b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe14
Else Uebersicht14=Uebersicht14a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,672,520,48) And NNEZKAW=0 Then
Uebersicht15=Uebersicht15b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe15
Else Uebersicht15=Uebersicht15a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,720,520,48) And NNEZKAW=0 Then
Uebersicht16=Uebersicht16b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe16
Else Uebersicht16=Uebersicht16a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,768,520,48) And NNEZKAW=0 Then
Uebersicht17=Uebersicht17b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe17
Else Uebersicht17=Uebersicht17a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,816,520,48) And NNEZKAW=0 Then
Uebersicht18=Uebersicht18b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe18
Else Uebersicht18=Uebersicht18a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,864,520,48) And NNEZKAW=0 Then
Uebersicht19=Uebersicht19b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Aufgabe19
Else Uebersicht19=Uebersicht19a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,912,520,48) And NNEZKAW=0 Then
Uebersicht20=Uebersicht20b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto LAufgabe
Else Uebersicht20=Uebersicht20a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,380,960,520,48) And NNEZKAW=0 Then
Uebersicht21=Uebersicht21b NNEZKAW=1
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxU) Then Goto Auswahl
Else Uebersicht21=Uebersicht21a
EndIf
Forever

.Uebersicht3
ResumeChannel HGM
jhhfgfsSFGjsgjmsgmsztzsh=1
Goto Uebersicht
End







.Belohnungsteil

If FileType(".\Games")=0 Then
	IISF("Games wurde nicht installiert")
	Delay 2500
	Goto Auswahl
EndIf

If Games_erlauben=0 Then
	IISF("Games wurden unter Optionen deaktiviert")
	Delay 2500
	Goto Auswahl
EndIf


NNEZKAW=0
AuswahOBL=0
SetBuffer ImageBuffer(gfxCircle)
Color 255,0,0
Oval 0,0,20,20,1
SetBuffer BackBuffer()
Color 0,0,255


Game1=Game1a
Game2=Game2a
Game3=Game3a
Game4=Game4a
Game5=Game5a
Game6=Game6a

StartZeit = MilliSecs()
Const ZeitMaxGM = 1000  ; 1 Sekunden



Repeat
circleX=MouseX()
circleY=MouseY()
NNEZKAW=0
DrawBlock Gletscher, 0,0
DrawBlock Game1, 340,270
DrawBlock Game2, 340,350
DrawBlock Game3, 340,430
DrawBlock Game4, 340,510
DrawBlock Game5, 340,590
DrawBlock Game6, 340,670



DrawImage gfxCircle,circleX,circleY
Flip
JetztZeit = MilliSecs()

If ImageRectOverlap (gfxCircle,circleX,circleY,340,270,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxGM) Then

	WarnungF$="Willst du BlitzBoulders für 60 Münzen spielen? Du hast "+Belohnungsmuenzen+" Belohnungsmünzen."
	WarnungA
	If JaO=1 Then
		If Belohnungsmuenzen<60 Then
			IISF("Du hast zu wenig Belohnungsmünzen!")
			Delay 2500
		Else
			Belohnungsmuenzen=Belohnungsmuenzen-60
			SpielstandSLR()
		ChangeDir ".\Games\BlitzBoulders\"
		ExecFile ".\BlitzBoulders.exe"
		ChangeDir "../../"
		EndIf
	EndIf
EndIf

SetBuffer BackBuffer()


Game1=Game1b
NNEZKAW=1
Else
Game1=Game1a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,350,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxGM) Then
	WarnungF$="Willst du Blitzanoid für 50 Münzen spielen? Du hast "+Belohnungsmuenzen+" Belohnungsmünzen."
	WarnungA
	If JaO=1 Then
		If Belohnungsmuenzen<50 Then
			IISF("Du hast zu wenig Belohnungsmünzen!")
			Delay 2500
		Else
			Belohnungsmuenzen=Belohnungsmuenzen-50
					SpielstandSLR()
		ChangeDir ".\Games\Blitzanoid\"
		ExecFile ".\Blitzanoid.exe"
		ChangeDir "../../"
		EndIf
	EndIf
EndIf

SetBuffer BackBuffer()
Game2=Game2b
NNEZKAW=1
Else
Game2=Game2a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,430,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxGM) Then
	WarnungF$="Willst du Mariodash für 40 Münzen spielen? Du hast "+Belohnungsmuenzen+" Belohnungsmünzen."
	WarnungA
	If JaO=1 Then
		If Belohnungsmuenzen<40 Then
			IISF("Du hast zu wenig Belohnungsmünzen!")
			Delay 2500
		Else
			Belohnungsmuenzen=Belohnungsmuenzen-40
			SpielstandSLR()
		ChangeDir ".\Games\Mariodash\"
		ExecFile ".\mdash.exe"
		ChangeDir "../../"
		EndIf
	EndIf
EndIf

SetBuffer BackBuffer()
Game3=Game3b
NNEZKAW=1
Else
Game3=Game3a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,510,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxGM) Then
	WarnungF$="Willst den Mariodash Editor für 10 Münzen öffnen? Du hast "+Belohnungsmuenzen+" Belohnungsmünzen."
	WarnungA
	If JaO=1 Then
		If Belohnungsmuenzen<10 Then
			IISF("Du hast zu wenig Belohnungsmünzen!")
			Delay 2500
		Else
			Belohnungsmuenzen=Belohnungsmuenzen-10
			SpielstandSLR()
		ChangeDir ".\Games\Mariodash\"
		ExecFile ".\medit.exe"
		ChangeDir "../../"
		EndIf
	EndIf
EndIf

SetBuffer BackBuffer()
Game4=Game4b
NNEZKAW=1
Else
Game4=Game4a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,590,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxGM) Then
	WarnungF$="Willst du HappyEgg für 30 Münzen spielen? Du hast "+Belohnungsmuenzen+" Belohnungsmünzen."
	WarnungA
	If JaO=1 Then
		If Belohnungsmuenzen<30 Then
			IISF("Du hast zu wenig Belohnungsmünzen!")
			Delay 2500
		Else
			Belohnungsmuenzen=Belohnungsmuenzen-30
			SpielstandSLR()
		ChangeDir ".\Games\BlitzBoulders\"
		ExecFile ".\BlitzBoulders.exe"
		ChangeDir "../../"
		EndIf
	EndIf
EndIf

SetBuffer BackBuffer()
Game5=Game5b
NNEZKAW=1
Else
Game5=Game5a
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,670,600,80) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxGM) Then Goto Auswahl
Game6=Game6b
NNEZKAW=1
Else
Game6=Game6a
EndIf
Delay 50
Forever





.Protokoll
ClsVB


Dim Protokoll_Aufgabennamen$(20)

Restore Protokoll_Data
For i=0 To 20
Read Protokoll_Aufgabennamen$(i)
Next



TBDH=LoadImage(".\Bilder\TBDH.jpg")
TBHD=LoadImage(".\Bilder\TBHD.jpg")
TBH=LoadImage(".\Bilder\TBH.jpg")

HGrundH=LoadImage (".\Bilder\TBH.jpg")

r=120
g=70
b=0
PROAZOZ=100
hfjdvbkvsl=0
PLienie=2
filein = ReadFile(Name$+".txt")
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
ReadLine$(filein)
i=0
While Not Eof(filein)
i=i+1
RST$(i)=ReadLine$(filein)
;If RST$(i)="" Then Exit
If i=9999 Then Exit
PLienie=PLienie+1
Wend
Color 255,255,255

CloseFile filein

;PLienie=PLienie-3
If PLienie>19 Then PLienie=PLienie-19 Else PLienie=1 ;19+3=22


For i=0 To 975 Step 65
DrawBlock TBDH,0,i
Next

For i=0 To 975 Step 65
DrawBlock TBHD,992,i
Next



Viewport 288,0,704,1024
ClsColor 253,243,0;253,202,13
Cls
Color 160,140,20
Schrift30 = LoadFont ("Arial",37,True)
SchriftN = LoadFont ("Arial",35) ;Achtung nicht Fett
SetFont SchriftN



;Wichtig! Variabeln müssen vor Protokollstart auf Startwert gesetz werden!
PDBEGA=0
NUESSCH=0
i=PLienie
PROAZOZ=150



While HJGzjtgft=0;GetKey()=0

Cls

Viewport 288,200,704,824
	While Not NUESSCH*35+PROAZOZ+PDBEGA>1200
		NUESSCH=NUESSCH+1
		i=i+1
		;If RST$(i)="" Then Exit
		
		
		SetFont SchriftN
		Color 110,110,110
		
		For Protokoll_Aufgabennamen_Zaeler=0 To 20		
			If RST$(i)=Protokoll_Aufgabennamen(Protokoll_Aufgabennamen_Zaeler) Then
				SetFont Arial_35
				PDBEGA=PDBEGA+20
				Color 0,0,0
				Exit
			EndIf
		Next
		
		PDatK$=Left$(RST$(i),6)
		If PDatK="Montag" Or PDatK="Dienst" Or PDatK="Mittwo" Or PDatK="Donner" Or PDatK="Freita" Or PDatK="Samsta" Or PDatK="Sonnta" Then SetFont Arial_40 : Color 0,0,0; PDBEGA=PDBEGA+10
		Text 640,((NUESSCH*35)+PROAZOZ)+PDBEGA,RST$(i),True
	Wend
Viewport 288,0,704,1024
	
	i=i-NUESSCH
	MZSP=0
	
	Color 255,225,25

	For iw=0 To 7 ;7 stimmt genau!
	DrawImage HGrundH,iw*87+300,130
	Next

	
	SetFont Arial_55
	Color r,g,b
	Text 640,10,"Protokoll Zeile "+(i)+"/"+(PLienie),True
	SetFont Arial_25
	Text 640,75,"Scrolle mit der Maus langsam oder mit den Pfeilsasten schnell",True
	Text 640,100,"Zur Hauptauswahl mit beliebiger Taste",True
	Color 0,0,0
	
	VWait
	

	
	UnlockBuffer FrontBuffer()

	While MZSP=0
		MZSP=MouseZSpeed()
		;If Not GetKey()=0 Then Viewport 0,0,1280,1024 : Goto Auswahl
		
		;Titelfarbe Ändert: Testfunktion!!!!!!!
		
		
		If KeyDown(200)=True Then MZSP=+1 : Exit
		If KeyDown(208)=True Then MZSP=-1 : Exit
		
		;If KeyDown(78)=True Then
		;	If KeyDown(19)=True And r<250 Then r=r+10 : Delay 100 : Exit
		;	If KeyDown(34)=True And g<250 Then g=g+10 : Delay 100 : Exit
		;	If KeyDown(48)=True And b<250 Then b=b+10 : Delay 100 : Exit
		;EndIf
		;
		;If KeyDown(74)=True Then
		;	If KeyDown(19)=True And r>0 Then r=r-10 : Delay 100 : Exit
		;	If KeyDown(34)=True And g>0 Then g=g-10 : Delay 100 : Exit
		;	If KeyDown(48)=True And b>0 Then b=b-10 : Delay 100 : Exit
		;EndIf
		If Not GetKey()=0 Then Goto Auswahl
		Delay 20
	Wend

	
	FlushKeys
	
	LockBuffer FrontBuffer()
	
	
	MZSP=MZSP*(-1)
	i=i+MZSP
	If i>PLienie Then i=PLienie
	If i<1 Then i=1
	PROAZOZ=150
	PDBEGA=0
	NUESSCH=0

Wend



Viewport 0,0,1280,1024
Goto Auswahl
End



.Protokoll_Data
Data "Plusrechnen", "Wörterdiktat", "Textverständnis (Der Standhafte Zinnsoldat)", "3", "Labyrinth (Mit Hilfe der Lösung)" ;5
Data "Labyrinth", "Verben", "Wörter erraten", "Zeitverständnis", "Witze", "Artikel", "Zahlen erraten", "Minusrechnen" ;7
Data "Wortfeld: sagen", "Malrechnen", "Wörter merken", "Rechenspiel", "Schnellrechnen", "Adjektive", "Pronomen", "Letzte Aufgabe" ;8













.SpielfigurW
Color 0,0,0
ClsColor 0,0,0
SetBuffer FrontBuffer()
Cls
FlushKeys
TFormFilter 0
SetFont Arial_50
DrawImage Gletscher, 0,0
Fig1=LoadImage (".\Bilder\Figur1.bmp")
Fig2=LoadImage (".\Bilder\Figur2.bmp")
Fig3=LoadImage (".\Bilder\Figur3.bmp")
Fig4=LoadImage (".\Bilder\Figur4.bmp")
Fig5=LoadImage (".\Bilder\Figur5.bmp")
Fig6=LoadImage (".\Bilder\Figur6.bmp")
Fig7=LoadImage (".\Bilder\Figur7.bmp")
Fig8=LoadImage (".\Bilder\Figur8.bmp")
ResizeImage Fig1,ImageWidth(Fig1)*3,ImageHeight(Fig1)*3
ResizeImage Fig2,ImageWidth(Fig2)*3,ImageHeight(Fig2)*3
ResizeImage Fig3,ImageWidth(Fig3)*3,ImageHeight(Fig3)*3
ResizeImage Fig4,ImageWidth(Fig4)*3,ImageHeight(Fig4)*3
ResizeImage Fig5,ImageWidth(Fig5)*3,ImageHeight(Fig5)*3
ResizeImage Fig6,ImageWidth(Fig6)*3,ImageHeight(Fig6)*3
ResizeImage Fig7,ImageWidth(Fig7)*3,ImageHeight(Fig7)*3
ResizeImage Fig8,ImageWidth(Fig8)*3,ImageHeight(Fig8)*3
DrawImage Fig1, 5,120
DrawImage Fig2, 330,120
DrawImage Fig3, 660,120
DrawImage Fig4, 985,120
DrawImage Fig5, 5,500
DrawImage Fig6, 330,500
DrawImage Fig7, 660,500
DrawImage Fig8, 985,500
Text 612,20,"Welche Spielfigur willst du haben?",1,1
Text 612,70,"Gib die Nummer der Spielfigur ein.",1,1
Text 115,420,"1"
Text 458,420,"2"
Text 785,420,"3"
Text 1100,420,"4"
Text 115,800,"5"
Text 458,800,"6"
Text 785,800,"7"
Text 1100,800,"8"
Repeat
Taste=WaitKey()
If Taste=49 Then
Spielfigur$=".\Bilder\Figur1.bmp"
Exit
ElseIf Taste=50 Then
Spielfigur$=".\Bilder\Figur2.bmp"
Exit
ElseIf Taste=51 Then
Spielfigur$=".\Bilder\Figur3.bmp"
Exit
ElseIf Taste=52 Then
Spielfigur$=".\Bilder\Figur4.bmp"
Exit
ElseIf Taste=53 Then
Spielfigur$=".\Bilder\Figur5.bmp"
Exit
ElseIf Taste=54 Then
Spielfigur$=".\Bilder\Figur6.bmp"
Exit
ElseIf Taste=55 Then
Spielfigur$=".\Bilder\Figur7.bmp"
Exit
ElseIf Taste=56 Then
Spielfigur$=".\Bilder\Figur8.bmp"
Exit
EndIf
Forever
ClsVB
Spielfigur_Laden(Spielfigur$)


Delay 10
Cls
SpielstandSLR
If firstspw=1 Then Return
Goto Auswahl
End



Function Spielfigur_Laden(Spielfigur$)
For i=1 To 11
SF(i)=LoadImage (Spielfigur$)
Next
TFormFilter 0
VWait
ResizeImage SF(1),ImageWidth(SF(1))*3,ImageHeight(SF(1))*3
ResizeImage SF(2),ImageWidth(SF(2))*2.8,ImageHeight(SF(2))*2.8
ResizeImage SF(3),ImageWidth(SF(3))*2.6,ImageHeight(SF(3))*2.6
ResizeImage SF(4),ImageWidth(SF(4))*2.4,ImageHeight(SF(4))*2.4
ResizeImage SF(5),ImageWidth(SF(5))*2.2,ImageHeight(SF(5))*2.2
ResizeImage SF(6),ImageWidth(SF(6))*2,ImageHeight(SF(6))*2
ResizeImage SF(7),ImageWidth(SF(7))*1.8,ImageHeight(SF(7))*1.8
ResizeImage SF(8),ImageWidth(SF(8))*1.6,ImageHeight(SF(8))*1.6
ResizeImage SF(9),ImageWidth(SF(9))*1.4,ImageHeight(SF(9))*1.4
ResizeImage SF(10),ImageWidth(SF(10))*1.2,ImageHeight(SF(10))*1.2
VWait
End Function






.SchwierikeitsstufeW
ClsVB
MoveMouse 185,303
Const ZeitMaxSW=1000  ; 1 Sekunden
SetBuffer ImageBuffer(gfxCircle)
Color 255,0,0
Oval 0,0,20,20,1
SetBuffer BackBuffer()
Color 0,0,255
SWKA1=SWK1
SWKA2=SWK2
SWKA3=SWK3
SWKA4=SWK4
SWKA5=SWK5

StartZeit = MilliSecs()

Repeat
circleX=MouseX()
circleY=MouseY()
NNEZKAW=0
DrawBlock SW, 0,0
DrawBlock SWKA1, 140,262
DrawBlock SWKA2, 140,362
DrawBlock SWKA3, 140,462
DrawBlock SWKA4, 140,562
If SWZH=0 DrawBlock SWKA5, 140,662
DrawImage gfxCircle,circleX,circleY
Flip
Delay 50
JetztZeit = MilliSecs()

If ImageRectOverlap (gfxCircle,circleX,circleY,140,262,1000,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSW) Then Schwierigkeitsstufe=1 Exit
SWKA1=SWK1O
NNEZKAW=1
Else
SWKA1=SWK1
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,140,362,1000,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSW) Then Schwierigkeitsstufe=2 Exit
SWKA2=SWK2O
NNEZKAW=1
Else
SWKA2=SWK2
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,140,462,1000,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSW) Then Schwierigkeitsstufe=3 Exit
SWKA3=SWK3O
NNEZKAW=1
Else
SWKA3=SWK3
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,140,562,1000,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSW) Then Schwierigkeitsstufe=4 Exit
SWKA4=SWK4O
NNEZKAW=1
Else
SWKA4=SWK4
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,140,662,1000,100) And SWZH=0 And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSW) Then Exit
SWKA5=SWK5O
NNEZKAW=1
Else
SWKA5=SWK5
EndIf
Forever

SpielstandSLR
If SWZH=1 Then SWZH=0 Goto Vorfilm
Goto Auswahl
End









.Spielstandoptionen
CLSVB
MoveMouse 375,251
SetBuffer ImageBuffer(gfxCircle)
Color 255,0,0
Oval 0,0,20,20,1
SetBuffer BackBuffer()
Color 0,0,255
SPKA1=SPK1
SPKA2=SPK2
SPKA3=SPK3
SPKA4=SPK4
SPKA5=SPK5
SPKA6=SPK6
SPKA7=SPK7

StartZeit = MilliSecs()
Const ZeitMaxSK=1000  ; 1 Sekunden
Repeat
circleX=MouseX()
circleY=MouseY()
NNEZKAW=0
DrawBlock SPK, 0,0
DrawBlock SPKA1, 340,162
DrawBlock SPKA2, 340,262
DrawBlock SPKA3, 340,362
DrawBlock SPKA4, 340,462
DrawBlock SPKA5, 340,562
DrawBlock SPKA6, 340,662
DrawBlock SPKA7, 340,762
DrawImage gfxCircle,circleX,circleY
Flip
Delay 50
JetztZeit = MilliSecs()

If ImageRectOverlap (gfxCircle,circleX,circleY,340,162,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then
quellpfad$ = ".\"+Name$+".txt"
zielpfad$ = ".\"+Name$+"BAK.txt"
CopyFile quellpfad$, zielpfad$
IISF("Sicherungsdatei wurde erstellt!")
Delay 2000
SetBuffer BackBuffer()
SpielstandKopierenA=0

EndIf
SPKA1=SPK1O
NNEZKAW=1
Else
SPKA1=SPK1
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,262,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then
	WarnungF$="Willst du wirklich deinen Spielstand mit der letzten Sicherungsdatei überschreiben?"
	WarnungA
	If JaO=1 Then
		CLSVB
		FreeImage HGrundH
		HGrundH=LoadImage (".\Bilder\Titelbild.jpg")
		TileBlock HGrundH
		Color 253,243,0
		Rect 72,64,1153,195,1
		SetFont Arial_60
		Color 0,0,0
		ClsColor 2,12,255

		If FileType(Name$+"BAK.txt") = 1 Then
			DeleteFile ".\"+Name$+".txt"
			quellpfad$ = ".\"+Name$+"BAK.txt"
			zielpfad$ = ".\"+Name$+".txt"
			CopyFile quellpfad$, zielpfad$
			Text 640,90,"Spielstand wurde erfolgreich mit der",True
			Text 640,170,"letzten Sicherungsdatei überschrieben!",True
		Else
			Text 640,130,"Sicherungsdatei wurde nicht gefunden!",True
		EndIf
		Delay 3000
	EndIf
	SetBuffer BackBuffer()
	SpielstandKopierenA=0
EndIf
SPKA2=SPK2O
NNEZKAW=1
Else
	SPKA2=SPK2
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,362,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then

	.Spielstand_ueberschreiben
	ClsVB
	Locate 510,160
	TileBlock TB
	Color 253,243,0
	Rect 72,64,1153,195,1

	SetFont Arial_50
	Color 0,0,0
	ClsColor 2,12,255
	Text 640,75,"Gib bitte den Namen ein, mit dessen Spielstand du",True
	Text 640,125,"deinen überschreiben willst.",True
	FlushMouse
	FlushKeys

	SetFont Arial_60
	NameS$=InputNB("", "", 100, 175, 1080, 70, 20, 1, 253, 243, 0, 0, 0, 0, "") ;Hir wird auch die Textfarbe bestimmt!

	

	filename$=NameS$+".txt"
	If FileType(filename$)=1 Then
		quellpfad$ = ".\"+NameS$+".txt"
		zielpfad$ = ".\"+Name$+".txt"
		CopyFile quellpfad$, zielpfad$
		IISF("Spielstand wurde erfolgreich überschrieben")
		Delay 1500
		Goto PStart
	Else
		IISF("Spielstand existiert nicht!")
		Delay 2000
		SpielstandKopierenA=0
	EndIf
	SetBuffer BackBuffer()
EndIf


;Teil der Auswahl Spielstand Kopieren
SPKA3=SPK3O
NNEZKAW=1
Else
SPKA3=SPK3
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,462,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then Goto SpielstandUn
SPKA4=SPK4O
NNEZKAW=1
Else
SPKA4=SPK4
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,562,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then ;Spielstand löschen

	GTJNEP=0
	WarnungF$="Willst du wirklich deinen Spielstand mit allen Sicherungsdateien löschen?"
	WarnungA
	If JaO=1 Then
		DeleteFile Name$+".txt"
		DeleteFile Name$+"BAK.txt"
		If FileType (Name$+".txt")=1 Or FileType (Name$+".txt")=1 Then
			IISF("Löschen fehlgeschlagen! evt. schreibgeschützt")
		Else
				IISF("Spielstand wurde erfolgreich gelöscht")
			EndIf
			Delay 2500
		FreeSound HM
		Goto PStart
	EndIf
	SetBuffer BackBuffer()
EndIf


SPKA5=SPK5O
NNEZKAW=1
Else
SPKA5=SPK5
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,662,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then

	CLSVB
	DrawBlock SElba, 0,0
	Color 0,0,0
	Locate 0,20
	SetFont Arial_100
	Print " Spielstand bearbeiten"
	SetFont Arial_40
	Print ""
	Print "	Da der Spielstandbearbeitungsteil nur für Eltern gedacht ist,"
	Print "	ist er durch einen vierstelligen Zahlencode geschützt."
	Print "	Die Resultate der 4 folgenden Rechnungen ergeben den Code."
	Print "	Die Sicherheit stellen die Rechenoperatoren dar. Ich habe sie"
	Print "	absichtlich so gewählt, dass Primarschüler sie noch nicht kennen!"
	Print "	Wichtig ist noch zu wissen, dass ^ ein Hochzeichen ist und ^0.5 "
	Print "	einer Quadratwurzel entspricht."
	Print ""
	Print ""
	Print "	Zahl1: |10-16|"
	Print "	Zahl2: 9^0.5"
	Print "	Zahl3: 1^77"
	Print "	Zahl4: 49^0.5"
	Print ""
	Spielstandmanipulation_Code=Input("	Code: ")




	If Spielstandmanipulation_Code=6317 Then
		DrawBlock SElba, 0,0
		Locate 0,20
	
		Print "	In den folgenden Kunfigurationen könnenn Sie Aufgaben überspringnen,"
		Print "	das Spielen von Games erlauben/verbieten und Belohnungsmünzen verwalten."
		Print ""
		Print "	Um keine Änderungen durchzuführen, drücken Sie einfach Enter ohne etwas"
		Print "	ins Eingabefeld einzugeben."
		Print ""
		Print "	Achtung:"
		Print "	Fehleingaben z.B. Buchstaben werden nicht erkannt und führen immer"
		Print "	zum Zahlenwert 0"

		Print ""
		Print ""
		Print "	-Weiter mit beliebiger Taste"
		
		WaitKey()
		
		
		
		
		DrawBlock SElba, 0,0
		Locate 0,20
		
		Print "	Aufgaben:"
		Print "	Der Wert Aufgaben steht für die gelöste Anzahl Aufgaben im eigentlichen"
		Print "	Spiel (nicht die in der Übersicht!). Durch ändern dieses Wertes kann"
		Print "	beispielsweise eine für ihr Kind zu schwere Aufgaben übersprungen werden."
		Print "	Danach wird sich die Spielfigur auch automatisch an dem, an die neue"
		Print "	Aufgabe gebundenen Ort befinden."
		Print ""
		Print "	Übrigens:"
		Print "	Die letzte Aufgabe kann nucht übersprungen werden. Die maximal mögliche"
		Print "	eingegebene Zahl darf nicht grösser als 19 sein."
		Print ""
		Print "	Jetzige Anzahl Aufgaben: "+Aufgaben
		Aufgaben_Alt=Aufgaben
		Aufgaben_Input$=Input("	Neue Anzahl Aufgaben: ")
		If Aufgaben_Input$>19 Then Aufgaben_Input$="19"
		If Not Aufgaben_Input$="" Then Aufgaben=Aufgaben_Input$ : SpielstandSLR()
		
		
		If Aufgaben=Aufgaben_Alt Then
			Print "	Es wurden keine Änderungen vorgenommen!"
		Else
			Print "	Geänderte Anzahl Aufgaben: "+Aufgaben
		EndIf
		

		Print ""
		Print ""
		Print "	-Weiter mit beliebiger Taste-"
		
		WaitKey()
		
		
		
		
		DrawBlock SElba, 0,0
		Locate 0,20	
		
		Print "	Belohnungsmünzen:"
		Print "	Der Wert Belohnungsmünzen steht für die Anzahl gesammelter Belohnungsmünzen."
		Print "	Mit in Aufgaben verdienten Belohnungsmünzen können Games, die nicht von mir"
		Print "	stammen, aber meinem Lernprogramm mitgeliefert werden, gespielt werden."
		Print "	Hier können Sie das Spielen von Games deaktiviueren/aktivieren sowie"
		Print "	die gesammelte Anzahl an Belohnungsmünzen ändern."
		Print ""
		
		If Games_Erlauben=1 Then
			Print "	Momentan ist das Spielen von Games erlaubt."
		Else
			Print "	Momentan ist das Spielen von Games nicht erlaubt."
		EndIf
		
		Print ""
		Print "	Wollen sie das Spielen von Games erlauben?"
		Print "	Schreiben Sie 1 für Ja und 0 für Nein ins folgende Eingabefeld."
		
		Games_erlauben_alt=Games_erlauben
		Games_erlauben_Input$=Input("	Games Erlauben: ")
		If Not Games_erlauben_Input$="" Then Games_erlauben=Games_erlauben_Input$ : SpielstandSLR()
		
		
		If Games_erlauben=Games_erlauben_alt Then
			Print "	Es wurden keine Änderungen vorgenommen!"
		Else
			If Games_Erlauben=1 Then
				Print "	Das Spielen  von Games ist jetzt wieder erlaubt!"
			Else
				Print "	Das Spielen von Games ist ab jetzt nicht mehr erlaubt!."
			EndIf
		EndIf
		
		
		If Games_Erlauben=1 Then
			Print ""
			Print ""
			Print "	Im folgenden Eingabefeld können Sie die Anzahl Belohnungsmünzen ändern."
			Print "	Jetzige Anzahl Belohnungsmünzen: "+Belohnungsmuenzen
			Belohnungsmuenzen_alt=Belohnungsmuenzen
			Belohnungsmuenzen_Input$=Input("	Neue Anzahl Belohnungsmünzen: ")
			
			
			If Not Belohnungsmuenzen_Input$="" Then Belohnungsmuenzen=Belohnungsmuenzen_Input$ : SpielstandSLR()
			
			If Belohnungsmuenzen=Belohnungsmuenzenaben_Alt Then
				Print "	Es wurden keine Änderungen vorgenommen!"
			Else
				Print "	Geänderte Anzahl Aufgaben: "+Belohnungsmuenzen
			EndIf
		Else
			Print ""
		EndIf
		
		
		Print ""
		
		Print"	-Weiter mit beliebiger Taste-"
		
		WaitKey()
			
	Else
		IISF("Falscher Code")
		Delay 2500
	EndIf

	SetBuffer BackBuffer()





EndIf
SPKA6=SPK6O
NNEZKAW=1
Else
SPKA6=SPK6
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,340,762,600,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxSK) Then Goto Auswahl
SPKA7=SPK7O
NNEZKAW=1
Else
SPKA7=SPK7
EndIf
Forever












.Grafik
ClsVB
GWITGV=0
TileBlock Aufgabenstellung_Hintergrund
Locate 0,10
SetFont Arial_65
Print " Grafik"
SetFont Arial_10
Print ""
Schrift = LoadFont ("Arial",27,False)
SetFont Arial_30
Print "  1280,1024,16,1 (Nicht empfohlen)"
SetFont Schrift
Print "	Bei dieser Grafik wird mein Lernprogramm so verzogen, dass es den ganzen Bildschirm bedeckt."
Print "	Vorteile:"
Print "	Keine Ablenkungen von Hintergrundsfenstern."
Print "	Fenster wird immer automatisch maximiert."
Print "	Nachteile:"
Print "	Lernprogramm wird eventuell sehr verzogen."
Print "	Spiele die sehr viel Grafik benötigen werden wegen der Hochrechnumng der Grafik sehr langsam."
Print "	Das Fenster kann bei Problemen nicht einfach oben rechts geschlossen werden,"
Print "	da das Kreuzchen "+Chr$(34)+"schliessen"+Chr$(34)+" fehlt."
Print "	Wird nur von ca. 70% aller Bildschirmen unterstützt."
Print ""
SetFont Arial_30
Print "  1280,1024,16,2 (empfolen)"
SetFont Schrift
Print "	Bei dieser Grafik wird mein Lernprogramm so angezeigt wie es ist."
Print "	Vorteile:"
Print "	sehr schneller Grafikaufbau."
Print "	Nachteile:"
Print "	Bei grossen Bildschirmen können andere Fenster im Hintergrund stören und bei"
Print "	kleinen Bildschirmen kann ein Teil des Fensters fehlen z.B. der untere Rand."
Print ""
SetFont Arial_30
Print "  1280,1024,16,3 (empfohlen)"
SetFont Schrift
Print "	Vorteile"
Print "	Dieses Fenster ist skalierbar, dass heisst man kann es manuell vergrössern und verkleinern (mit der Maus"
Print "	am Fensterrand ziehen) und so sich selber eine eigene Fenstergrösse formen."
Print "	Nachteile:"
Print "	Wird, bei kleinen Bildschirmen (z.B. bei Laptops) nach jedem Programmstart wieder auf den Standard verkleinert."
Print "	Spiele die sehr viel Grafik benötigen werden wegen der Umrechnung der Grafik sehr langsam."
Print ""
Print "	Um die Grafik zu ändern, drücken Sie 1,2 oder 3 (je nach Grafiknummer)."
If Grafik_Hauptmenue_verbieten=0 Then Print "	Um keine Änderungen durchführen einfach beliebige Taste (ausser 1, 2 oder 3) drücken."
Print "	Wenn die Grafik nicht geändert werden kann,weil der Bildschrim die Grafik nicht unterstüzt,
Print "	wird einfach die jetztige Grafik beibehalten."
Print "	Die Grafik kann auch in der Datei Grafik.txt verändert werden. Dies ist Beispielsweise vorteilhaft,"
Print "	wenn sie mein Lernprogramm z.B. im 32bit Farben spielen wollen."
Taste=WaitKey()
Cls
Locate 0,0
Modus=GfxModeExists(1280,1024,16)
filename$=".\Grafik.txt"

If Taste=49 And Modus=1 Then
GWITGV=1
fileout = WriteFile(".\Grafik.txt")
WriteLine fileout,"Grafik: 1280,1024,0,1"
CloseFile fileout
EndIf

If Taste=50 Then
GWITGV=1
fileout = WriteFile(".\Grafik.txt")
WriteLine fileout,"Grafik: 1280,1024,0,2"
CloseFile fileout
EndIf

If Taste=51 Then
GWITGV=1
fileout = WriteFile(".\Grafik.txt")
WriteLine fileout,"Grafik: 1280,1024,0,3"
CloseFile fileout
EndIf

If GWITGV=1 Then
GWITGV=0
FreeSound HM
Goto PStart
EndIf
If Grafik_Hauptmenue_verbieten=0 Then Goto Auswahl Else Goto Grafik
End






.SpielstandUn


WarnungF$="Willst du wirklich deinen Spielstand mit allen Sicherungsdateien umbenennen?"
WarnungA
If JaO=1 Then
ClsVB

NameS$=Name$



.SpielstandUnN
ClsVB()
Locate 510,160
TileBlock TB
Color 253,243,0
Rect 72,64,1153,195,1

SetFont Arial_60
Color 0,0,0
ClsColor 2,12,255
Text 650,100,"Gib deinen neuen Namen ein und drücke Enter",True
FlushMouse
FlushKeys


Name$=InputNB("", "", 100, 160, 1080, 70, 20, 1, 253, 243, 0, 0, 0, 0, "") ;Hir wird auch die Textfarbe bestimmt!
;Function InputNB(InputNB_Fragetext$, InputNB_Text$, InputNB_X, InputNB_Y, InputNB_Rect_wight, InputNB_Rect_hight, InputNB_Anz_Buchstaben, InputNB_Center, InputNB_Rect_Color_r, InputNB_Rect_Color_g, InputNB_Rect_Color_b, InputNB_Text_Color_r, InputNB_Text_Color_g, InputNB_Text_Color_b, InputNB_Curser$)
;Name$ = Input()


If KeyHit(1)=True
End
EndIf
If Len (Name$)<3 Then
	Cls
	Locate 0,0
	Locate 510,160
	TileBlock TB
	Color 253,243,0
	Rect 72,64,1153,195,1

	SetFont SchriftF
	Color 0,0,0
	ClsColor 2,12,255
	Text 640,100,"Der Name muss mindestens aus drei Zeichen bestehen!",True
	FlushMouse
	FlushKeys
	Delay 2000
	Goto SpielstandUnN
EndIf


CopyFile ".\"+NameS$+".txt", ".\"+Name$+".txt"
DeleteFile ".\"+NameS$+".txt"
DeleteFile ".\"+NameS$+"BAK.txt";geht nicht!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
IISF("Spielstand wurde erfolgreich unbennent")
FreeSound HM
Goto PStart
Else
Goto Auswahl
EndIf
End





Function IISF(SFT$)
CLSVB
FreeImage HGrundH
HGrundH=LoadImage (".\Bilder\Titelbild.jpg")
Schrift = LoadFont ("Arial",60,True)
TileBlock HGrundH
Color 253,243,0
Rect 72,64,1153,195,1
SetFont Schrift
Color 0,0,0
ClsColor 2,12,255
Text 640,130,SFT$,1
FreeFont Schrift
VWait
End Function







.Programmstart
AppTitle "Lernen mit Smiley","Wollen Sei das das Programm wirklich beenden?"+Chr(13)+"Kleine Fortschritte können verloren gehen!"
If NANJN=1 Or Aufgaben=100 Then NAJN=0 NANJN=0 Else NAJN=1
FreeSound Game
ResumeChannel HGM

;Spielstandsicherung
SpielstandSN("")

;Sachen=0
x=0
y=0
X=0
Y=0
HGrundH=0
circleX=0
circleY=0
FlushKeys
FlushMouse

Const ZeitMaxNA=1000  ; 1 Sekunden

If NANJN=0 Then

CLSVB
SetBuffer BackBuffer()

NAKA1=NAK1
NAKA2=NAK2
NAKA3=NAK3

Repeat
circleX=MouseX()
circleY=MouseY()
NNEZKAW=0
DrawBlock SElba, 0,0
DrawBlock NAKA1, 240,362
DrawBlock NAKA2, 240,462
DrawBlock NAKA3, 240,562
DrawImage gfxCircle,circleX,circleY
Flip
JetztZeit = MilliSecs()

If ImageRectOverlap (gfxCircle,circleX,circleY,240,362,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then Exit
NAKA1=NAK1O
NNEZKAW=1
Else
NAKA1=NAK1
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,240,462,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then AppTitle "Lernen mit Smiley" : Goto Auswahl
NAKA2=NAK2O
NNEZKAW=1
Else
NAKA2=NAK2
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,240,562,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then AppTitle "Lernen mit Smiley" Goto EPro
NAKA3=NAK3O
NNEZKAW=1
Else
NAKA3=NAK3
EndIf
Delay 50
Forever

EndIf

If Aufgaben=1 Then Goto Teil2
If Aufgaben=2 Then Goto Teil3
If Aufgaben=3 Then Goto Teil4
If Aufgaben=4 Then Goto Teil5
If Aufgaben=5 Then Goto Teil6
If Aufgaben=6 Then Goto Teil7
If Aufgaben=7 Then Goto Teil8
If Aufgaben=8 Then Goto Teil9
If Aufgaben=9 Then Goto Teil10
If Aufgaben=10 Then Goto Teil11
If Aufgaben=11 Then Goto Teil12
If Aufgaben=12 Then Goto Teil13
If Aufgaben=13 Then Goto Teil14
If Aufgaben=14 Then Goto Teil15
If Aufgaben=15 Then Goto Teil16
If Aufgaben=16 Then Goto Teil17
If Aufgaben=17 Then Goto Teil18
If Aufgaben=18 Then Goto Teil19
If Aufgaben=19 Then Goto Teil20
If Aufgaben=100 Then Goto Uebersicht
Goto Vorfilm
End



.Teil1
ClsVB
FY=720
FX=500
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Höle1.jpg")
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 64*SFG#, 22*SFG#, 32*SFG#
Cls
Repeat
SYP
SFGG=SFGG+1
Until SFGG=5
Goto Aufgabe1
End



.Teil2
ClsVB
FY=950
FX=520
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Höle2&4.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=3
ClsVB
FY=950
FX=550
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Höle3.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=3
Goto Aufgabe2
End


.Teil3
ClsVB
FY=950
FX=520
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Höle2&4.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=3
ClsVB
FY=950
FX=750
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Höle5.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=2
Goto Aufgabe3
End



.Teil4
ClsVB
HGML#=1
FY=950
FX=520
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Höle6.jpg")
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
SYP
SYP
Repeat
ChannelVolume HGM,HGML#
HGML#=HGML#-0.0006
Delay 1
Until HGML#=<0
FreeSound HM
HM=LoadSound(".\Sounds\Harfe 2.mp3")
LoopSound HM
HGM=PlaySound (HM)
ChannelVolume HGM,HGML#
Repeat
ChannelVolume HGM,HGML#
HGML#=HGML#+0.0006
Delay 1
Until HGML#=>1
ClsVB
FY=365
FX=20
G=1
SFGG=0
GZSFG
HGrundSFOS=LoadImage (".\Bilder\Brücke.jpg")
SmileyB=LoadImage (".\Bilder\Smiley.bmp")
MaskImage SmileyB,255,255,255
DrawBlock HGrundSFOS,0,0
DrawImage SmileyB,328,261
DrawImage SmileyB,605,175
DrawImage SmileyB,874,235
HGrundSF = CreateImage (1280,1024)
SetBuffer ImageBuffer (HGrundSF)
CopyRect 0,0,1280,1024,0,0,FrontBuffer(),ImageBuffer(HGrundSF)
SetBuffer FrontBuffer()
Repeat
SXPB
SFGG=SFGG+1
Until SFGG=3
Goto Aufgabe4
End




.Teil5
ClsVB
FY=260
FX=261
G=1
SFGG=0
GZSFG
HGrundSFOS=LoadImage (".\Bilder\Brücke.jpg")
SmileyB=LoadImage (".\Bilder\Smiley.bmp")
MaskImage SmileyB,255,255,255
DrawBlock HGrundSFOS,0,0
DrawImage SmileyB,605,175
DrawImage SmileyB,874,235
HGrundSF = CreateImage (1280,1024)
SetBuffer ImageBuffer (HGrundSF)
CopyRect 0,0,1280,1024,0,0,FrontBuffer(),ImageBuffer(HGrundSF)
SetBuffer FrontBuffer()
Repeat
SXPB1
SFGG=SFGG+1
Until SFGG=3
Goto Aufgabe5
End



.Teil6
ClsVB
FY=140
FX=605
G=1
SFGG=0
GZSFG
HGrundSFOS=LoadImage (".\Bilder\Brücke.jpg")
SmileyB=LoadImage (".\Bilder\Smiley.bmp")
MaskImage SmileyB,255,255,255
DrawBlock HGrundSFOS,0,0
DrawImage SmileyB,874,235
HGrundSF = CreateImage (1280,1024)
SetBuffer ImageBuffer (HGrundSF)
CopyRect 0,0,1280,1024,0,0,FrontBuffer(),ImageBuffer(HGrundSF)
SetBuffer FrontBuffer()
Repeat
SXPB2
SFGG=SFGG+1
Until SFGG=2
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+20
FY=FY+5
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
Delay 200
FX=FX+20
FY=FY+5
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+20
FY=FY+5
Goto Aufgabe6
End



.Teil7
ClsVB
FY=200
FX=874
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\Brücke.jpg")
TB1=LoadImage (".\Bilder\01.jpg")
Repeat
SXPB3
SFGG=SFGG+1
Until SFGG=5
SeedRnd MilliSecs()
ClsVB
img = CreateImage (1280,1024)
SetBuffer ImageBuffer (img)
DrawImage HGrundSF,0,0
VWait
SetBuffer FrontBuffer()
DrawBlock HGrundSF,0,0
Dim matrix(xdiv,ydiv)
For ii = 1 To frames
	For I = 1 To choice
		Repeat
			x=Rnd(0,xdiv)
			y=Rnd(0,ydiv)
		Until matrix(x,y)=0
		matrix(x,y)=ii
	Next
Next
dly=CreateTimer(fps)
Color 0,0,0
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				Rect x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
SeedRnd MilliSecs()
HGML#=1
ClsVB
img = CreateImage (1280,1024)
SetBuffer ImageBuffer (img)
DrawBlock TB1,0,0
SetBuffer FrontBuffer()
Repeat
ChannelVolume HGM,HGML#
HGML#=HGML#-0.0006
Delay 1
Until HGML#=<0
FreeSound HM
HM=LoadSound(".\Sounds\Harfe 1.mp3")
LoopSound HM
HGM=PlaySound (HM)
ChannelVolume HGM,HGML#
Repeat
ChannelVolume HGM,HGML#
HGML#=HGML#+0.0006
Delay 1
Until HGML#=>1
Dim matrix(xdiv,ydiv)
For ii = 1 To frames
	For I = 1 To choice
		Repeat
			x=Rnd(0,xdiv)
			y=Rnd(0,ydiv)
			Until matrix(x,y)=0
		matrix(x,y)=ii
	Next
Next
dly=CreateTimer(fps)
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				DrawImageRect img,x*xsize,y*ysize,x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
Delay 1000
ClsVB
FY=950
FX=600
G=1
SFGG=0
GZSFG
HGrundSF=TB1
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=5
SYP
Goto Aufgabe7
End



.Teil8
ClsVB
FY=950
FX=635
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\02.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=3
Repeat
SFGG=SFGG+1
SYP
Until SFGG=12
Goto Aufgabe8
End


.Teil9
ClsVB
FY=950
FX=675
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\03.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=4
Goto Aufgabe9
End


.Teil10
ClsVB
FY=950
FX=500
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\04.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
SYP
g=g+1
GZSFG
SYP
SFGG=SFGG+1
Until SFGG=3
SYP
Goto Aufgabe10
End


.Teil11
ClsVB
FY=950
FX=570
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\05.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=5
Goto Aufgabe11
End

.Teil12
ClsVB
FY=950
FX=675
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\06.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=4
SXM
SXM
SXM
SXM
SXM
SXM
SXM
SXM
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1

SYP
SYP
SYP
Goto Aufgabe12
End

.Teil13
ClsVB
FY=950
FX=675
G=3
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\07.jpg")
SYP
SYP
SYP
SXM
SXM
SXM
SXM
g=g+1
GZSFG
SYP
SYP
SYP
SYP
g=g+1
GZSFG
SYP
SYP
SYP
SXP
SYP
SXP
g=g+1
GZSFG
SYP
SYP
SYP
SYP
SXP
SYP
SYP
Goto Aufgabe13
End

.Teil14
ClsVB
FY=650
FX=1200
G=2
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\08.jpg")
SXM
SXM
SXM
SXM
SXM
g=g+1
GZSFG
SYP
SXM
SXM
SXM
SXM
SXM
g=g+1
GZSFG
SYP
SXM
SXM
SXM
SXM
SXM
g=g+1
GZSFG
SXM
SXM
SYP
SXM
SXM
SXM
g=g+1
GZSFG
SYP
SYP
Goto Aufgabe14
End

.Teil15
ClsVB
FY=950
FX=690
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\09.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=2
SYP
Goto Aufgabe15
End

.Teil16
ClsVB
FY=950
FX=50
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\10.jpg")
Repeat
SYP
SYP
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=7
SYP
Goto Aufgabe16
End

.Teil17
ClsVB
FY=950
FX=450
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\11.jpg")
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=2
SYP
SXP
g=g+1
GZSFG
SXP
SXP
SXP
g=g+1
GZSFG
SYP
SYP

Goto Aufgabe17
End

.Teil18
ClsVB
SetFont Arial_55
FY=950
FX=730
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\12.jpg")
BildSU=HGrundSF
CLSVB
Repeat
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=2
SYP
SYP
Bild = CreateImage (1280,1024)
SetBuffer ImageBuffer (Bild)
CopyRect 0,0,1280,1024,0,0,FrontBuffer(),ImageBuffer(Bild)
SetBuffer FrontBuffer()
Color 0,255,0
Text 640,10,"Laden...",True
VWait
Delay 1000
SetBuffer BackBuffer()
DrawBlock Bild,0,0
LockBuffer BackBuffer()
Repeat
rgb=ReadPixelFast(x,y,BackBuffer)
r=(rgb And $FF0000)/$10000
g=(rgb And $FF00)/$100
b=rgb And $FF
r=r/4
g=g/4
b=b/4
Farbe=r*$10000 + g*$100 + b
WritePixelFast x,y,Farbe
x=x+1
If x=1280 Then y=y+1 x=0
If y=1023 And x=1279 Then
UnlockBuffer BackBuffer()
Exit
EndIf
Forever
SetBuffer FrontBuffer()
Flip
SetFont Arial_40
Color 0,255,0
Text 640,10,"Du bist nicht mehr weit von deinem Haus entfernt,",True
Text 640,60,"Achtung: Vor deinem Haus wartet schon dein Feind",True
Text 640,110,"auf dich!",True
Text 640,160,"Ich war ihm begegnet,  konnte mich aber zum Glück noch",True
Text 640,210,"ganz knapp in diesem hohlen Baumstamm verstecken!",True
SetFont Arial_30
Text 640,970,"Weiter mit beliebiger Taste",True
WaitKey
CLSVB
G=4
SFGG=0
GZSFG
SXM
SXM
SXM
SYP
SYP
SYP
g=g+1
GZSFG
SYP
SYP
g=g+1
GZSFG
SYP
g=g+1
GZSFG
SFGG=SFGG+1
SYP
SYP
Goto Aufgabe18
End

.Teil19
ClsVB
FY=950
FX=350
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\13.jpg")
Repeat
SYP
SYP
SYP
g=g+1
GZSFG
SFGG=SFGG+1
Until SFGG=6
SYP
SYP
Goto Aufgabe19
End

.Teil20
ClsVB
FY=950
FX=570
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\14e.jpg")
SYP
SYP
SYP
g=g+1
GZSFG
SFGG=SFGG+1
SYP
SYP
SYP
Goto LAufgabe
End







.Aufgabe1
Aufgabe_Aufgabenname$="Plusrechnen"
Goto Aufgabe_1_12_14
End






.Aufgabe2

Dim Aufgabe2_Woerter$(19)

ClsVB
SpielstandSN("Wörterdiktat")
Aufgabe_abgebrochen=1 : SpielstandSLR
PauseChannel HGM
TileBlock Aufgabenstellung_Hintergrund
Locate 0,20
SetFont Arial_100
Print " Wörterdiktat"
SetFont Arial_40
Print ""
Print "	Zuerst wird ein Wort je nach Schwierigkeitsstufe"
Print "	0.75 bis 3 Sekunden lang gezeigt, dann wird es gelöscht."
Print "	Du musst es dann fehlerfrei schreiben (und Enter drücken)."
Print "	Damit man nicht über das Bild schreiben kann, wurde die"
Print "	maximale Anzahl Eingabebuchstaben auf 9 beschränkt!"
Print "	Es gibt also keine Wörter mit über 9 Buchstaben!"
Print "	Jede richtige Aufgabe ergibt 1 Belohnungsmünze."
Print "	Jede falsche gibt dagegen 1 Münze Abzug."
Print ""
Print "	Viel Glück!"
Print ""
Print ""
Print "	-Drücke eine belibige Taste um die Aufgabe zu starten-"


FreeImage HGrundH
SeedRnd MilliSecs()
WBA1=Rand (0,6)
If WBA1=0 Then HGrundH=LoadImage (".\Bilder\Wald.jpg")
If WBA1=1 Then HGrundH=LoadImage (".\Bilder\Regenbogen.jpg")
If WBA1=2 Then HGrundH=LoadImage (".\Bilder\Zugersee.jpg")
If WBA1=3 Then HGrundH=LoadImage (".\Bilder\Schmetterling.jpg")
If WBA1=4 Then HGrundH=LoadImage (".\Bilder\ZugerseeP.jpg")
If WBA1=5 Then HGrundH=LoadImage (".\Bilder\Gletscher.jpg")
If WBA1=6 Then HGrundH=LoadImage (".\Bilder\BrückeP.jpg")

WaitKey()



.Aufgabe2_Dateienauswahl

ClsColor 0,0,0
Cls
Locate 0,0

TileBlock Aufgabenstellung_Hintergrund
SetFont Arial_100
Print " Wortdateienauswahl"
SetFont Arial_40
Print ""
Print "	Wähle eine Wortdatei aus, indem du ihre Nummer eingibst und mit Enter"
Print "	bestätigst. Einige Wortdatieen sind beigelegt. Es können aber neue  hinzu-"
Print "	gefügt oder die beigelegten bearbeitet werden. Siehe Bedinungsanleitung!"
Print ""
Write "	Folgende Wortdateien sind verfügbar:	"


Aufgabe_Dateienindex_Zaeler=0
Aufgabe_WortdateieNr=1

Aufgabe_Zeilen_Y=400
Aufgabe_Zeilen_X=25

Text Aufgabe_Zeilen_X, Aufgabe_Zeilen_Y, "Hauptordner"

Color 75,75,75

Ortner_tree(".\Data\Wörterdiktat\")

SetFont Arial_40
Color 0,0,0

Aufgabe2_Dateinummer_Input=Input("			Dateinummer: ") ;Aufgabe2_Dateinummer_Input muss nicht auf null gesetzt werden, da sie von der Eingabe überschrieben wird.

Cls
Locate 0,0
TileBlock Aufgabenstellung_Hintergrund

Aufgabe2_Woerter_Einlesezaehler=0

Datei$=""

If Not Aufgabe2_Dateinummer_Input=0 Then Datei$=Aufgabe_Dateienindex$(Aufgabe2_Dateinummer_Input-1) ;Wenn Datei$=0 ist dann Datei$=""=Fehlermeldung anzeigen



If Not FileType (Datei$)=1 Then
		SetFont Arial_100
		Print " Fehler:"
		SetFont Arial_40
		Print ""
		Print "	Datei existiert nicht!"
		Print "	Bitte vergewissere dich, dass du wirklich die korrekte"
		Print "	Dateinummer angegeben hast und versuche es erneut!"
		Print ""
		Print "	-Zürück zur Dateienauswahl mit beliebiger Taste-"
		WaitKey()
		Goto Aufgabe2_Dateienauswahl
EndIf


DateiID = ReadFile(Datei$)
While Not Eof(DateiID)
	Aufgabe2_Eingelesene_Zeile$=ReadLine$(DateiID)
	If Not Left$(Aufgabe2_Eingelesene_Zeile$,1)=";" Then
	If Not Len(Aufgabe2_Eingelesene_Zeile$)=0 Then
		If Len(Aufgabe2_Eingelesene_Zeile$)<2 Or Len(Aufgabe2_Eingelesene_Zeile$)>9 Then
				SetFont Arial_100
				Print " Fehler:"
				SetFont Arial_40
			Print ""
			Print "		Zeile "+(Aufgabe2_Woerter_Einlesezaehler+1)+" ist ungültig!"
			Print "		Wort muss zwischen 2 und 9 Zeichen enthalten!"
			Print "		Bitte lade eine korrekte Datei oder repariere sie!"
			Print "		Siehe Bedinungsanleitung!"
			Print ""
			Print "		-Zürück zur Dateienauswahl mit beliebiger Taste-"
			Print ""
			Print ""
			Print ""
			Print "		Zeile "+(Aufgabe2_Woerter_Einlesezaehler+1)+" = "+Chr$(34)+Aufgabe2_Eingelesene_Zeile$+Chr$(34)
			WaitKey()
			Goto Aufgabe2_Dateienauswahl
		EndIf
		If Aufgabe2_Woerter_Einlesezaehler=20 Then
				SetFont Arial_100
				Print " Warnung:"
				SetFont Arial_40
				Print ""
				Print "	Die Datei enthält mehr als 20 gültige Wörter!"
				Print "	Für diese Aufgabe werden einfach nur die ersten"
				Print "	20 verwendet!"
				Print ""
				Print "	-Weiter mit beliebiger Taste-"
				WaitKey()
				Exit
			EndIf

		Aufgabe2_Woerter$(Aufgabe2_Woerter_Einlesezaehler)=Aufgabe2_Eingelesene_Zeile$ ;Achtung: Nullbasierend!
		;Print Aufgabe2_Woerter$(Aufgabe2_Woerter_Einlesezaehler)
		Aufgabe2_Woerter_Einlesezaehler=Aufgabe2_Woerter_Einlesezaehler+1
		;Input()
	EndIf
	EndIf
	
Wend

If Aufgabe2_Woerter_Einlesezaehler<19 Then
	Locate 0,20
	SetFont Arial_100
	Print " Fehler:"
	SetFont Arial_40
	Print ""
	Print "	Die Datei enthält nur "+Aufgabe2_Woerter_Einlesezaehler+" gültige Wörter!"
	Print "	Für diese Aufgabe werden aber mindestens 20 benötigt"
	Print "	Bitte Lade eine korrekte Datei oder füge noch"
	Print "	einige neue Wörter hinzu!"
	Print "	Siehe Bedinungsanleitung!"
	Print ""
	Print "	-Zürück zur Dateienauswahl mit beliebiger Taste-"
	WaitKey()
	Goto Aufgabe2_Dateienauswahl
EndIf

ClsColor 0,0,0
Cls
Locate 0,0


StartZeit = MilliSecs()

;Startvariabeln Setzen
Aufgabe2_Richtig=0
Aufgabe2_Falsch=0
Aufgabe2_Richtig_Temp=0
Aufgabe2_Falsch_Temp=0
Aufgabe2_Aufgabe=0

Aufgabe2_Zufallswort$=""

Aufgabe2_Input$=""



FlushKeys


SetFont Arial_25
Color 255,255,255

SeedRnd MilliSecs()


Aufgabe1_Text_Anz=0

Origin 200,0 ;Verschiebung vom Nullpunkt um 200PX nach rechts







Repeat
;Color 255,200,0


;Aufgabe2_Zufallswort$ = Wort$(Rand(0,Maxs))


If Schwierigkeitsstufe=1 Then Zahl1= Rand(0,50) Zahl2= Rand(0,50)
If Schwierigkeitsstufe=2 Then Zahl1= Rand(0,100) Zahl2= Rand(0,100)
If Schwierigkeitsstufe=3 Then Zahl1= Rand(0,1000) Zahl2= Rand(0,999)
If Schwierigkeitsstufe=4 Then Zahl1= Rand(0,9000) Zahl2= Rand(0,999) ;Zahlen sind ein bischen komisch, da das Ergebniss sollte, wegen Platzgründen, nicht über 9999 sein!



FlushKeys
FlushMouse


Repeat

Color 255, 155, 0
Text -193, (Aufgabe2_Text_Anz*71)+15,Aufgabe2_Woerter$(Aufgabe2_Aufgabe)

If Schwierigkeitsstufe=1 Then Delay 2000
If Schwierigkeitsstufe=2 Then Delay 1500
If Schwierigkeitsstufe=3 Then Delay 1000
If Schwierigkeitsstufe=4 Then Delay 500

Color 0,0,0
Text -193, (Aufgabe2_Text_Anz*71)+15,Aufgabe2_Woerter$(Aufgabe2_Aufgabe)


Aufgabe2_Input$=InputNB("", "", -193, (Aufgabe2_Text_Anz*71)+15, 193, 70, 9, 0, 0, 0, 0, 255, 255, 50, "|") ;Hir wird auch die Textfarbe bestimmt!
;Ergebnis=Input(Zahl1 +"+"+Zahl2 + "=")

If Aufgabe2_Input$=Aufgabe2_Woerter$(Aufgabe2_Aufgabe) Then
	Color 0,255,0
	Text -193,(Aufgabe2_Text_Anz*71)+45,"Richtig!"
	Aufgabe2_Richtig_Temp=1
	If Aufgabe2_Falsch_Temp=0 Then Aufgabe2_Richtig=Aufgabe2_Richtig+1
Else
	Color 255,0,0
	Text -193,(Aufgabe2_Text_Anz*71)+45,"Falsch!"
	Aufgabe2_Falsch_Temp=1
EndIf

FlushKeys


Aufgabe2_Text_Anz=Aufgabe2_Text_Anz+1

If Aufgabe2_Text_Anz=14 Then
	Aufgabe2_Text_Anz=0
	Delay 1000
	Color 0,0,0
	Rect -200,0,200,1024,1
	;Cls
EndIf




;Cls
;Locate 0,0

	
VWait
Until Aufgabe2_Richtig_Temp=1

Aufgabe2_Falsch=Aufgabe2_Falsch+Aufgabe2_Falsch_Temp

Aufgabe2_Richtig_Temp=0
Aufgabe2_Falsch_Temp=0
Aufgabe2_Aufgabe=Aufgabe2_Aufgabe+1


If Aufgabe2_Aufgabe=1 Then DrawImageRect HGrundH,0,0,0,0,216,256
If Aufgabe2_Aufgabe=2 Then DrawImageRect HGrundH,216,0,216,0,216,256
If Aufgabe2_Aufgabe=3 Then DrawImageRect HGrundH,432,0,432,0,216,256
If Aufgabe2_Aufgabe=4 Then DrawImageRect HGrundH,648,0,648,0,216,256
If Aufgabe2_Aufgabe=5 Then DrawImageRect HGrundH,864,0,864,0,216,256
If Aufgabe2_Aufgabe=6 Then DrawImageRect HGrundH,0,256,0,256,216,256
If Aufgabe2_Aufgabe=7 Then DrawImageRect HGrundH,216,256,216,256,216,256
If Aufgabe2_Aufgabe=8 Then DrawImageRect HGrundH,432,256,432,256,216,256
If Aufgabe2_Aufgabe=9 Then DrawImageRect HGrundH,648,256,648,256,216,256
If Aufgabe2_Aufgabe=10 Then DrawImageRect HGrundH,864,256,864,256,216,256
If Aufgabe2_Aufgabe=11 Then DrawImageRect HGrundH,0,512,0,512,216,256
If Aufgabe2_Aufgabe=12 Then DrawImageRect HGrundH,216,512,216,512,216,256
If Aufgabe2_Aufgabe=13 Then DrawImageRect HGrundH,432,512,432,512,216,256
If Aufgabe2_Aufgabe=14 Then DrawImageRect HGrundH,648,512,648,512,216,256
If Aufgabe2_Aufgabe=15 Then DrawImageRect HGrundH,864,512,864,512,216,256
If Aufgabe2_Aufgabe=16 Then DrawImageRect HGrundH,0,768,0,768,216,256
If Aufgabe2_Aufgabe=17 Then DrawImageRect HGrundH,216,768,216,768,216,256
If Aufgabe2_Aufgabe=18 Then DrawImageRect HGrundH,432,768,432,768,216,256
If Aufgabe2_Aufgabe=19 Then DrawImageRect HGrundH,648,768,648,768,216,256
If Aufgabe2_Aufgabe=20 Then DrawImageRect HGrundH,864,768,864,768,216,256 : Delay 2000

Until Aufgabe2_Aufgabe=20

Belohnungsmuenzen=Belohnungsmuenzen+Aufgabe2_Richtig-Aufgabe2_Falsch : SpielstandSLR


If WortRichtig=0 Then WDRVP=WDRVP+1
;Until WortRichtig=1
WortRichtig=0
FlushKeys
FlushMouse
;Until Worteraten = 20



;Spielstandsicherung
Origin 0,0
Cls
Locate 0,0
FlushKeys
FlushMouse
TileBlock Aufgabenstellung_Hintergrund
Color 0,0,0
SetFont Arial_50

Text 640,50,"Du hast "+(20-WDRVP)+"/20 Aufgaben richtig gelöst!",True

If Aufgabe2_Richtig>=12 Then
	Text 640,150,"Herzlichen Glückwunsch!",True
	Text 640,200,"Du warst spitze!",True
ElseIf Aufgabe2_Richtig>=10
	Text 640,150,"Herzlichen Glückwunsch!",True
	Text 640,200,"Du warst OK!,True
Else
	Text 640,150,"Deine Leistungen sind noch",True
	Text 640,200,"ein bisschen verbesserungswürdig",True
	Text 640,250,"Um deine Leistungen zu verbessern,",True
	Text 640,300,"solltest du diese Aufgabe einige Male",True
	Text 640,350,"freiwillig wiederholen!",True
EndIf

Text 640,950,"-Weiter mit beliebiger Taste-",True
WaitKey()

Aufgabe_abgebrochen=0 : SpielstandSLR
SpielstandSN("Punkte: "+(20-WDRVP)+"/20")
Gosub PZeit
If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
FlushKeys
FlushMouse
Goto Programmstart
End




;.Woerter2

;Data "ihm", "ihn", "ihr", "ihnen", "riechen", "fernsehen", "bezahlen"
;Data "ruhig", "mehr", "mehrere", "wohnen", "während", "stehen", "schief"
;Data "sehr", "nehmen", "ehrlich", "fröhlich", "allmählich", "fahren", "tief"
;Data "ähnlich", "wahr", "gefährlich", "gehen", "ohne", "wohl", "zuviel"
;Data "erzählen", "bleiben", "bleibt", "blieb", "geblieben", "bringen", "bringt"
;Data "brachte", "gebracht", "denken", "denkt", "dachte", "gedacht"
;Data "erschrecken", "erschrickt", "erschrak", "erschrocken"
;Data "essen", "isst", "ass", "gegessen", "fahren", "fährt", "fuhr", "gefahren"
;Data "fallen", "fällt", "fiel", "gefallen", "finden", "findet", "fand"
;Data "gefunden", "fliegen", "fliegt", "flog", "geflogen", "geben", "gibt"
;Data "gefällt", "gefiel", "gehen", "geht", "ging", "gegangen"
;Data "geschehen", "geschieht", "geschah", "haben", "hat", "hatte", "gehabt"
;Data "halten", "hält", "hielt", "gehalten", "kommen", "kommt", "kam", "gekommen" 
;Data "können", "kann", "konnte", "gekonnt", "laden", "lädt", "lud", "geladen"
;Data "laufen", "läuft", "lief", "gelaufen", "legen", "legt", "legte", "gelegen"
;Data "leiden", "leidet", "litt", "gelitten", "lesen", "liest", "las", "gelesen"
;Data "liegen", "liegt", "lag", "gelegen", "messen", "misst", "mass", "gemessen"
;Data "nehmen", "nimmt", "nahm", "genommen", "rufen", "ruft", "rief", "gerufen"
;Data "schlafen", "schläft", "schlief", "geschlafen", "schreiben", "schreibt"
;Data "schrieb", "geschrieben", "sehen" ,"hören" , "bemalen" , "arbeiten"







.Aufgabe3
SpielstandSN("Zahlen erraten")
Aufgabe_abgebrochen=1 : SpielstandSLR
VSJBL=0
NAWH_Image=SElba
Aufgabe3_Versuche=0
ClsVB
FreeImage HGrundH
HGrundH=LoadImage (".\Bilder\Zugersee.jpg")
DrawBlock HGrundH, 0,0
SeedRnd MilliSecs()

If Schwierigkeitsstufe<3 Then Aufgabe3_MaxRandZahl=100 : Aufgabe3_MaxAnzVersuche=10
If Schwierigkeitsstufe=3 Then Aufgabe3_MaxRandZahl=1000 : Aufgabe3_MaxAnzVersuche=15
If Schwierigkeitsstufe=4 Then Aufgabe3_MaxRandZahl=10000 : Aufgabe3_MaxAnzVersuche=20


Aufgabe3_Zufallszahl = Rand (1,Aufgabe3_MaxRandZahl)


SetFont Arial_90
Text 640,5,"Zahlen erraten",True
SetFont Arial_40
Text 640,100,"Ich denke mir eine Zahl zwischen 1 und "+Aufgabe3_MaxRandZahl+", errate sie!",True
Text 640,150,"Das Ziel ist, die Zahl in "+Aufgabe3_MaxAnzVersuche+" Versuchen herauszufinden.",True
Text 640,200,"Wirst du sie erraten? Wenn Ja dann erbringt es dir 2 Behlonungsmünzen.",True
Text 640,250,"Falls du dies nicht schaffst, musst du die Aufgabe wiederholen!",True
Text 640,300,"Münzen werden für diese Aufgabe keine abgezogen!",True

Text 640,380,"Viel Glück und Spass!",True

Text 640,700,"-Weiter mit beliebiger Taste-",True


WaitKey()
StartZeit = MilliSecs()
Cls
Locate 0,0
FreeImage HGrundH
HGrundH=LoadImage (".\Bilder\stars.bmp")
DrawBlock SElba, 0,0

Print ""

Repeat
Color 125,125,125
Write "		Rate mal: "
Color 0,0,0
  Zahl = Input()
If Zahl=0 Then
Color 237,28,36
Print "		Fehleingabe" ; Randabstand ist gleich wie bei den Anderen Texten
Else
  Aufgabe3_Versuche = Aufgabe3_Versuche + 1
  If Zahl < Aufgabe3_Zufallszahl Then Color 0,162,232 : Print "		Zu klein!"
  If Zahl > Aufgabe3_Zufallszahl Then Color 34,177,76 : Print "		Zu gross!"
EndIf
VSJBL=VSJBL+1
If VSJBL=10 Then
VSJBL=0
Delay 500
Cls
Locate 0,0
DrawBlock SElba, 0,0
Print ""
EndIf

Until Zahl = Aufgabe3_Zufallszahl


Aufgabe_abgebrochen=0
Belohnungsmuenzen=Belohnungsmuenzen+2
SpielstandSLR

SpielstandSN("Versuche: "+Aufgabe3_Versuche)
Gosub PZeit


Cls
Locate 0,0
SetFont Arial_40

SetBuffer BackBuffer()

i=1024
MoveMouse 640,i

Aufgabe3_Schrift_r#=255
Aufgabe3_Schrift_g#=255
Aufgabe3_Schrift_b#=255



While GetKey()=0


If Aufgabe3_Schrift_r#>0 Then Aufgabe3_Schrift_r#=Aufgabe3_Schrift_r#-0.1
If Aufgabe3_Schrift_g#>0 Then Aufgabe3_Schrift_g#=Aufgabe3_Schrift_g#-0.1
If Aufgabe3_Schrift_b#>0 Then Aufgabe3_Schrift_b#=Aufgabe3_Schrift_b#-0.1

	UpdateFrags()
	
	Cls
	
	
	TileBlock HGrundH
	
	Color Aufgabe3_Schrift_r,Aufgabe3_Schrift_g,Aufgabe3_Schrift_b
	Text 640,150,"Richtig!",True
	Text 640,200,"Du hast " +Aufgabe3_Versuche+ " Mal geraten.",True
	
	Text 640,700,"-Weiter mit beliebiger Taste-",True

	If Aufgabe3_Versuche>Aufgabe3_MaxAnzVersuche Then
	Text 640,250,"Du hast das Ziel nicht erreicht, versuche die Aufgabe nochmals.",True
	WaitKey()
	Gosub NAWH
	Goto Aufgabe3
	Else
	Text 640,250,"Herzlichen Glückwunsch, du hast das Ziel erreicht!",True
	
	Text 640,400,"Bewege doch mal deine Maus oder kliche mit der linken Maustaste",True
	Text 640,450,"So kannst du das, im Hintergrund laufende Farbspiel verändern",True

	;WaitKey()
	EndIf
	
	
	If i>0 Then
		i=i-2	
		MoveMouse 640,i
	EndIf
	
	
	If MouseDown(1)
		Color 255,255,255
		Rect MouseX(),MouseY()-3,1,7
		Rect MouseX()-3,MouseY(),7,1

	Else
		CreateFrags()	
	EndIf
	
	RenderFrags()
	Flip
Wend


If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
FlushKeys
FlushMouse
Goto Programmstart
End
Return











	
.Aufgabe4
ClsVB
SpielstandSN("Nomen")
Aufgabe_abgebrochen=1 : SpielstandSLR
NVAorP=1
NAWH_Image=SElba
PauseChannel HGM
StartZeit = MilliSecs()
FlushKeys
Fehler=0

Cls
Locate 0,20
DrawBlock SElba, 0,0

SetFont Arial_100
Print " Nomen"
SetFont Arial_40
Print ""
Print "	Löse die folgenden Aufgaben."
Print "	Die Aufgabe gibt maximagl 12 Belohnungsmünzen."
Print "	für jede falsche Aufgabe gibt es zwei Abzug."
Print "	Je nach Schwierigkeitsstufe wird aber nach"
Print "	einer bestimmten Anzahl Fehler die Aufgabe"
Print "	abgebrochen und du musst von vorne beginnen."
Print "	Viel Spass!"
Print ""
Print "	-Weiter mit beliebiger Taste-"

WaitKey()


DrawBlock SElba, 0,0

SetFont Arial_30
Color 0,0,0
Locate 0,20

Print "	Erkenne das Nomen in dem folgenden Satz."
Print "	Schreibe das Nomen und drücke Enter"
Print
Print "	Dies ist ein einfacher Satz."
Ueberpruefewort$ = "Satz"
Gosub Grammatik_Ueberpruefung

Print "	Welcher Artikel hat das Nomen?"
Print "	Schreibe der, die oder das und drücke Enter"
Ueberpruefewort$ = "der"
Gosub Grammatik_Ueberpruefung

Print "	Schreibe die Mehrzahl von (Satz) und drücke Enter"
Ueberpruefewort$ = "Sätze"
Gosub Grammatik_Ueberpruefung
Delay 1500


;Brief
Locate 0,20
DrawBlock SElba, 0,0
Fehler = 0
Print "	Erkenne das Nomen in dem folgenden Satz."
Print "	Schreibe das Nomen und drücke Enter"
Print
Print "	Ich schreibe dir einen Brief."
Ueberpruefewort$ = "Brief"
Gosub Grammatik_Ueberpruefung

Print "	Welcher Artikel hat das Nomen?"
Print "	Schreibe der, die oder das und drücke Enter"
Ueberpruefewort$ = "der"
Gosub Grammatik_Ueberpruefung

Print "	Schreibe die Mehrzahl von (Brief) und drücke Enter"
Ueberpruefewort$ = "Briefe"
Gosub Grammatik_Ueberpruefung
Delay 1500


;Tagebuch
Locate 0,20
DrawImage SElba, 0,0
Fehler = 0
Print "	Erkenne das Nomen in dem folgenden Satz."
Print "	Schreibe das Nomen und drücke Enter"
Print
Print "	Hast du heute schon ins Tagebuch geschrieben?"
Ueberpruefewort$ = "Tagebuch"
Gosub Grammatik_Ueberpruefung

Print "	Welcher Artikel hat das Nomen?"
Print "	Schreibe der, die oder das und drücke Enter"
Ueberpruefewort$ = "das"
Gosub Grammatik_Ueberpruefung

Print "	Schreibe die Mehrzahl von (Tagebuch) und drücke Enter"
Ueberpruefewort$ = "Tagebücher"
Gosub Grammatik_Ueberpruefung
Delay 1500


;Dorf
Locate 0,20
DrawBlock SElba, 0,0
Fehler = 0
Print "	Erkenne das Nomen in dem folgenden Satz."
Print "	Schreibe das Nomen und drücke Enter"
Print
Print "	Ich wohne im Dorf."
Ueberpruefewort$ = "Dorf"
Gosub Grammatik_Ueberpruefung

Print "	Welcher Artikel hat das Nomen?"
Print "	Schreibe der, die oder das und drücke Enter"
Ueberpruefewort$ = "das"
Gosub Grammatik_Ueberpruefung

Print "	Schreibe die Mehrzahl von (Dorf) und drücke Enter"
Ueberpruefewort$ = "Dörfer"
Gosub Grammatik_Ueberpruefung
Delay 1500


Belohnungsmuenzen=Belohnungsmuenzen+12 : SpielstandSLR

Aufgabe_abgebrochen=0 : SpielstandSLR
Gosub PZeit
If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
Goto Programmstart
End




.Aufgabe5
ClsVB
SpielstandSN("Labyrinth")
Aufgabe_abgebrochen=1 : SpielstandSLR
LPfeilSF=255*$10000 + 255*$100
Global Looo
.maze_endofintro

; the main structure for a maze, use 'maze_create' to create one of these
Type maze
	Field width,height
	Field buffer
	Field crossing
End Type

; internal representation of a maze cell, don't use in your code
Type maze_loc
	Field x,y
End Type

; internal control structure, don't use in your code
Type maze_control
	Field imgname$
	Field imgwidth, imgheight
	Field img
	Field count
End Type

Function maze_getcell(m.maze,x,y)
	Return PeekByte(m\buffer,y*m\width+x)
End Function

Function maze_setcell(m.maze,x,y,value)
	PokeByte m\buffer,y*m\width+x,value
End Function

Function maze_GetDir(m.maze,x,y,dir)
	Return (maze_getcell(m,x,y) And (2^dir))
End Function

Function maze_SetDir(m.maze,x,y,dir,value)
	c = maze_getcell(m,x,y) And (255-(2^dir))
	If value = 1 Then
		c = c Or (2^dir)
	End If
	maze_setcell(m,x,y,c)
End Function

Function maze_DirOpen(m.maze,x,y,dir)
	Return (maze_getDir(m,x,y,dir) = (2^dir))
End Function

Function maze_NorthOpen(m.maze,x,y)
	Return maze_diropen(m,x,y,0)
End Function

Function maze_EastOpen(m.maze,x,y)
	Return maze_diropen(m,x,y,1)
End Function

Function maze_SouthOpen(m.maze,x,y)
	Return maze_diropen(m,x,y,2)
End Function

Function maze_WestOpen(m.maze,x,y)
	Return maze_diropen(m,x,y,3)
End Function

; display the maze on screen
Function maze_display(m.maze,route)

	l.maze_loc = New maze_loc
	For x = 0 To m\width - 1
		For y = 0 To m\height - 1
			l\x = x
			l\y = y
			maze_drawcell(m,l)
			If route Then
				If (maze_getcell(m,x,y) And 240) > 0 Then
					maze_drawincell(x,y,0,255,0)
				End If
			End If
		Next
	Next
	Delete l

End Function

; draw a single maze cell
Function maze_drawcell(m.maze,l.maze_loc)
	
	mc.maze_control = First maze_control 
	If mc <> Null Then
	
		x=l\x*mc\imgwidth
		y=l\y*mc\imgheight
	
		p = maze_getcell(m,l\x,l\y) Mod 16
	
		DrawImage mc\img,x,y,p
	End If
	
End Function

Function maze_drawIncell(px,py,r,g,b)
	
	mc.maze_control = First maze_control 
	If mc <> Null Then
	
		x=px*mc\imgwidth
		y=py*mc\imgheight
		
		Color r,g,b
		Rect x+2,y+2,mc\imgwidth-4,mc\imgheight-4,1
	End If
	
End Function



Function maze_Create.maze(width,height,seed,crossovers)
	m.maze = New maze
	
	m\width = width
	m\height = height
	m\crossing = (crossovers > 0)
	
	m\buffer = CreateBank(width * height)	
	mc.maze_control = First maze_control
	If mc <> Null Then
		mc\count = mc\count + 1
	End If
	
	maze_generate(m,crossovers,seed)
	
	Return m
End Function

; delete an individual maze
Function maze_Delete(m.maze)

	mc.maze_control = First maze_control
	If mc <> Null Then
		mc\count = mc\count + 1
	End If

	FreeBank m\buffer
	Delete m  
End Function

; initialisation routine
; call at the start of the program, but after the graphics command
Function maze_startup(filename$,imgwidth,imgheight)
	mc.maze_control = New maze_control
	mc\imgwidth = imgwidth
	mc\imgheight = imgheight
	mc\imgname$ = filename$
;	mc\img = LoadAnimImage(filename$,32,32,0,16)
	mc\img = LoadAnimImage(".\Bilder\mazeblocks.png",32,32,0,16)
	ResizeImage mc\img,imgwidth,imgheight

End Function

; cleanup routine, call at the end of the program to clear
; up any mazes left hanging round
Function maze_shutdown()
	For m.maze = Each maze
		maze_delete(m)
	Next
	
	mc.maze_control = First maze_control
	If mc <> Null Then
		FreeImage mc\img
		Delete mc
	End If
	
End Function


; calculate offset direction
Function maze_dircalc(l.maze_loc,dir,r.maze_loc)
	Select dir
		Case 0
			dx = 0
			dy = -1
		Case 1
			dx = 1
			dy = 0
		Case 2
			dx = 0
			dy = 1
		Case 3
			dx = -1
			dy = 0
	End Select
	
	r\x = l\x+dx
	r\y = l\y+dy
	
End Function	

;Invert offset direction
Function maze_Invdir(dir)
	Return (dir + 2) Mod 4
End Function


Function maze_validdirs(m.maze,l.maze_loc,flags)

;	DrawImage img,x,y,p

	c = 0
;	mc = First(maze_control)
;	If mc <> Null Then

		r.maze_loc = New maze_loc
		For i = 0 To 3
			maze_dircalc(l,i,r)
			
			If (r\x >= 0) And (r\x < m\width) And (r\y >= 0) And (r\y < m\height) Then
				If (flags And 1) Then
					If maze_getcell(m,r\x,r\y) > 0 Then c=c+1
				End If
				
				If (flags And 2) Then 
					If maze_getcell(m,r\x,r\y) = 0 Then c=c+1				
				End If
				
				If (flags And 4) Then
					If (maze_getcell(m,l\x,l\y) And (2^i)) =0 Then c = c + 1
				End If
			End If
	
		Next 
		Delete r
		

	
	Return c

End Function

; flag xxxxxxx1 = new dir must be empty cell
;      xxxxxx1x = new dir must be 'used' cell
;      xxxxx1xx = new dir must not have an opening already
Function maze_dir(m.maze,l.maze_loc,flags,r.maze_loc)


	safe = 0
	result = -1
	
	If maze_validdirs(m,l,flags) = 0 Then result = 0
	
	While result = -1
	
;		d=(Rnd(1)*4)-.5
		d=Rand(0,3)
		safe = safe + 1
		If safe > 100 Then Stop
			
		maze_dircalc(l,d,r)	
		
		If (r\x >= 0) And (r\x < m\width) And (r\y >= 0) And (r\y < m\height) Then
			If (flags And 1) Then
				If maze_getcell(m,r\x,r\y) > 0 Then result = 1
			End If
			
			If (flags And 2) Then 
				If maze_getcell(m,r\x,r\y) = 0 Then result = 1		
			End If

			If (flags And 4) Then
				If (maze_getcell(m,l\x,l\y) And (2^d)) =0 Then result = 1
			End If
			
		End If
		
	Wend
			
	If result = 0 Then			
		Return -1
	Else
		Return d
	End If

End Function

Function maze_generate(m.maze,crossovers,seed)
	SeedRnd seed
	finished = 0
	
	For x = 0 To m\width-1
		For y = 0 To m\height -1
			maze_setcell(m,x,y,0) 
		Next
	Next 
	
	l.maze_loc = New maze_loc
	r.maze_loc = New maze_loc
	b.maze_loc = New maze_loc
	b\x = 0
	b\y = 0
	l\x=b\x
	l\y=b\y

	maze_setcell(m,l\x,l\y,16) 
	While Not finished
		; move to new location
		If maze_dir(m,l,2,r) > -1 Then
			l\x = r\x
			l\y = r\y
		Else
			newstart = 0
			While Not newstart
				b\x = b\x + 1
				
				If b\x = m\width Then
					b\x = 0
					b\y = b\y + 1
					If b\y = m\height Then
						finished = 1
						newstart = 1
					End If
				End If
				
				If Not finished Then
					If maze_getcell(m,b\x,b\y) = 0 Then 
						newstart = 1
					End If
				End If
			Wend
	
			l\x = b\x
			l\y = b\y
		End If
		
		If Not finished Then
			d = maze_dir(m,l,1,r)
			If d > -1 Then
				maze_setcell(m,l\x,l\y,maze_getcell(m,l\x,l\y) Or (2^d))
				maze_setcell(m,r\x,r\y,maze_getcell(m,r\x,r\y) Or (2^((d + 2) Mod 4)))	
;				maze_drawcell(m,r)
			End If
		End If
	
	Wend
		
	maze_setcell(m,0,0,maze_getcell(m,0,0) - 16)
	
	
	; generate crossovers
	i = 0
	While i < crossovers
		fails = 0
		Repeat
			l\x = Rnd(1,m\width-2)
			l\y = Rnd(1,m\height-2)
			d = maze_dir(m,l,4,r)
			If d > -1 Then
				m0 = maze_getcell(m,l\x,l\y)
				m1 = maze_getcell(m,r\x,r\y)

				If (((m0 And 1)=1)+((m0 And 2)=2)+((m0 And 4)=4)+((m0 And 8)=8) > 1) Or (((m1 And 1)=1)+((m1 And 2)=2)+((m1 And 4)=4)+((m1 And 8)=8) > 1) Then
					d = -1
				Else
					If d = 0 Or d=2 Then
						If (((m0 And 2) = 2) And ((m1 And 2) = 2)) Or (((m0 And 8) = 8) And ((m1 And 8) = 8)) Then
							d = -1
						End If
					Else
						If (((m0 And 1) = 1) And ((m1 And 1) = 1)) Or (((m0 And 4) = 4) And ((m1 And 4) = 4)) Then
							d = -1
						End If				
					End If
				End If
			End If
			If d = -1 Then fails = fails + 1
		Until d > -1 Or fails > 50
		
		If d > -1 Then
				maze_setcell(m,l\x,l\y,maze_getcell(m,l\x,l\y) Or (2^d))
				maze_setcell(m,r\x,r\y,maze_getcell(m,r\x,r\y) Or (2^((d + 2) Mod 4)))	
		End If
	
		i = i + 1
	Wend
	
	
	Delete l
	Delete r
	Delete b

End Function

Type maze_node
	Field x,y
	Field dir,tries
	Field edir
End Type

Function maze_clearRoute(m.maze)
	; clear out route information
	For x = 0 To m\width-1
		For y = 0 To m\height-1
			maze_setcell(m,x,y,(maze_getcell(m,x,y) And 15))
		Next
	Next
End Function

Function maze_allSolve(m.maze,fx,fy)
	maze_clearroute(m.maze)
	For x = 0 To m\width-1
		For y = 0 To m\height-1
			If (x <> fx Or y <> fy) And ((maze_getcell(m,x,y) And 240) = 0) Then
				maze_solve(m,x,y,fx,fy)
			End If
		Next
	Next
End Function

; solve the maze using a fairly simple "wall hugger" style process
Function maze_solve(m.maze,sx,sy,fx,fy)
 If Not m\crossing Then
	; create a workspace same size as the map.
	count = 1
	
	n.maze_node = New maze_node
	n\x = sx
	n\y = sy
	n\dir = -1
	n\tries = 0
	n\edir = 16
	finished = 0
	
	
	l.maze_loc = New maze_loc
	r.maze_loc = New maze_loc
	
	While (n\x <> fx Or n\y <> fy) And (finished = 0)

	
		n\tries = n\tries + 1
		n\dir = (n\dir + 1) Mod 4
		If n\tries > 4 Then
			Delete n
			n = Last maze_node
		Else
			If maze_diropen(m,n\x,n\y,n\dir) And n\dir <> n\edir Then
				count = count + 1
				; that dir is open	
				l\x = n\x
				l\y = n\y
				dir = n\dir
				maze_dircalc(l,n\dir,r)
				n = New maze_node
				n\x = r\x
				n\y = r\y
				n\dir = dir
				n\tries = 0
				n\edir = maze_invdir(dir)
			End If
		End If
	Wend
	
	Delete l
	Delete r
	
	
	;create final codestring & inscribe on maze
	cs$ = ""
	For n = Each maze_node
		maze_setcell(m,n\x,n\y,(maze_getcell(m,n\x,n\y) And 15) Or (2^(n\dir+4)))
		cs$ = cs$ + Str$(n\dir)
	Next

	; clear the workspace at the end
	Delete Each maze_node
  End If
  Return cs$
End Function

Function Maze_Save(m.maze,filename$)
	; Open a file to write to
	fileout = WriteFile(filename$)
	WriteInt fileout,m\width
	WriteInt fileout,m\height
	WriteInt fileout,m\crossing
	WriteBytes m\buffer,fileout,0,m\width*m\height
	CloseFile fileout
End Function

Function Maze_Load.maze(filename$)
	m.maze = New maze
	filein = ReadFile(filename$)
	m\width = ReadInt( filein)
	m\height = ReadInt( filein)
	m\crossing = ReadInt(filein)
	m\buffer = CreateBank(m\width*m\height)
	ReadBytes m\buffer,filein,0,m\width*m\height
	
	Return m

End Function

FreeFont Schrift
Schrift = LoadFont ("Arial",27,True)
SetFont Schrift
Aus=1
	maze_startup(".\Bilder\mazeblocks.png",16,16)

	mwidth = 50;32; 	set a variable for the width 
	mheight = 50;32;	and the height of the maze


	SetBuffer BackBuffer()

	
	
	finished = 0
	x = 0
	y = 0
	LabLA=0
	
	a$="1"
	k = Asc(a$)
.StartL
	a$="1"
	 Looo=0
	While Not finished
		Cls
		
		If a$ = "1" Then
			If m.maze <> Null Then maze_Delete(m)
			m.maze = maze_create(mwidth,mheight,MilliSecs(),0)
			x = 0
			y = 0		
		End If
		
		If a$="2" And x <> mwidth-1 And y <> mheight-1 Then
		LabLA=1
			maze_clearRoute(m)
			maze_solve(m,x,y,mwidth-1,mheight-1)
		End If
		
		If a$="4" Then  maze_save(m,"maze.maz")
		If a$="5" And FileType("maze.maz") = 1 Then
			maze_delete(m)
			m = maze_load("maze.maz")
		End If
		
		If a$ = "3" Then maze_clearroute(m)
		
		
		If k = 28 And maze_NorthOpen(m,x,y) Then
			y=y-1
		End If

		If k = 29 And maze_SouthOpen(m,x,y) Then
			y=y+1
		End If

		If k = 30 And maze_EastOpen(m,x,y) Then
			x=x+1
		End If

		If k = 31 And maze_WestOpen(m,x,y) Then
			x=x-1
		End If

	maze_display(m,1)

	If a$ = "6" Or a$= "7" Then
	If a$ = "6" Then maze_clearroute(m)
	If a$ = "7" Then xx=x yy=y x=0 y=0 : maze_clearRoute(m):maze_solve(m,x,y,mwidth-1,mheight-1) :x=xx y=yy
	maze_display(m,1)
	Bild = CreateImage (800,800)
	SetBuffer ImageBuffer (Bild)
	CopyRect 0,0,800,800,0,0,FrontBuffer(),ImageBuffer(Bild)
	SetBuffer FrontBuffer()
	Repeat
	Cls
	Locate 0,0
	Print "Unterstützte Bildformate: Bmp, Jpg, Png, Pcx, Tga, Iff"
	Print "Pfad und Dateinamen um das Labyrinth zu speichern z.b. C:\Users\Nico\Desktop\newball.jpg:"
	Pfad$=Input()
	SaveImage (Bild,Pfad$)
	If FileType(Pfad$) = 0 Then Print "Ungültiger Pfad!" Delay 1000
	Cls
	Until FileType(Pfad$) = 1
	SetBuffer BackBuffer()
	maze_display(m,1)
	EndIf
	maze_drawincell(x,y,255,255,255)
	Text 810,0, "Bewege dich mit den Pfeiltasten!"
	Text 810,30,"Start: Oben links Ziel: Unten rechts"	
	Text 810,60,"1 = Neues Labyrinth"
	Text 810,90,"2 = Zeig mir die Lösung"
	Text 810,120,"3 = Lösungen nicht mehr anzeigen"
	Text 810,150,"4 = Labyrinth sichern"
	Text 810,180,"5 = Gesichertes Labyrinth laden"
	Text 810,210,"6 = Labyrinth exportieren (ohne Lösung)"
	Text 810,240,"7 = Labyrinth exportieren (mit Lösung)"
	Text 810,300,"Ein ohne Hilfe gelöstes Labyrinth gibt 5"
	Text 810,330,"andernfalls 2 Belohnungsmünzen!"
	Color 255,255,0
	Rect 815,790,20,3,1
	Rect 814,782,1,19,1
	Rect 813,783,1,17,1
	Rect 812,784,1,15,1
	Rect 811,785,1,13,1
	Rect 810,786,1,11,1
	Rect 809,787,1,9,1
	Rect 808,788,1,7,1
	Rect 807,789,1,5,1
	Rect 806,790,1,3,1
	WritePixel 805,791,LPfeilSF
	Text 843,790,"Ziel",0,1
    Color 255,255,255
	Flip


If x=49 And y=49 Then
Aufgabe_abgebrochen=0 : SpielstandSLR

If LabLA=1 Then
	SpielstandSN("Mit Hilfe der Lösung gelöst!")
	Belohnungsmuenzen=Belohnungsmuenzen+2 : SpielstandSLR
Else
	SpielstandSN("Ohne Hilfe der Lösung gelöst!")
	Belohnungsmuenzen=Belohnungsmuenzen+5 : SpielstandSLR
EndIf

If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
FlushKeys
FlushMouse
Goto Programmstart
EndIf
		k = WaitKey()
		a$ = Lower$(Chr$(k))
	Wend

	



.Aufgabe6
ClsVB
SpielstandSN("Wörter erraten")
Aufgabe_abgebrochen=1 : SpielstandSLR

Versuche=0
SGBS$=0
PauseChannel HGM
FlushKeys
DrawBlock SElba, 0,0
Restore Woerter

;Einleseschlaufe für 137 Wörter
      Const Max = 136
Dim Wort$(Max)
Locate 0,20
SetFont Arial_100
Print " Wörter erraten"
SetFont Arial_40
Print ""
Print "	Errate das Wort indem du einzelne Buchstaben ausprobierst.
Print "	Die ? geben dir an, wieviele Buchstaben das Wort hat."
Print "	Du kannst nur einen Buchstaben auf einmal erraten."
Print ""
Print "	Um einen Buchstaben zu erraten, drücke einfach diesen"
Print "	Buchstaben aus der Tastatur!
Print ""
Print "	Tipp:  Es hat keine Nomen."
Print String("	",5)+"Das heisst, dass du nur Kleinbuchstaben aus-"
Print String("	",5)+"probieren musst. Eingegebene Grossbuchstaben"
Print String("	",5)+"werden automatisch zu Kleinbuchstaben umgewandelt."
Print ""
Print "	-Weiter mit beliebiger Taste-"

.Woerter
Data "ihm", "ihn", "ihr", "ihnen", "riechen", "fernsehen", "bezahlen"
Data "ruhig", "mehr", "mehrere", "wohnen", "während", "stehen", "schief"
Data "sehr", "nehmen", "ehrlich", "fröhlich", "allmählich", "fahren", "tief"
Data "ähnlich", "wahr", "gefährlich", "gehen", "ohne", "wohl", "zuviel"
Data "erzählen", "bleiben", "bleibt", "blieb", "geblieben", "bringen", "bringt"
Data "brachte", "gebracht", "denken", "denkt", "dachte", "gedacht"
Data "erschrecken", "erschrickt", "erschrak", "erschrocken"
Data "essen", "isst", "ass", "gegessen", "fahren", "fährt", "fuhr", "gefahren"
Data "fallen", "fällt", "fiel", "gefallen", "finden", "findet", "fand"
Data "gefunden", "fliegen", "fliegt", "flog", "geflogen", "geben", "gibt"
Data "gefällt", "gefiel", "gehen", "geht", "ging", "gegangen"
Data "geschehen", "geschieht", "geschah", "haben", "hat", "hatte", "gehabt"
Data "halten", "hält", "hielt", "gehalten", "kommen", "kommt", "kam", "gekommen" 
Data "können", "kann", "konnte", "gekonnt", "laden", "lädt", "lud", "geladen"
Data "laufen", "läuft", "lief", "gelaufen", "legen", "legt", "legte", "gelegen"
Data "leiden", "leidet", "litt", "gelitten", "lesen", "liest", "las", "gelesen"
Data "liegen", "liegt", "lag", "gelegen", "messen", "misst", "mass", "gemessen"
Data "nehmen", "nimmt", "nahm", "genommen", "rufen", "ruft", "rief", "gerufen"
Data "schlafen", "schläft", "schlief", "geschlafen", "schreiben", "schreibt"
Data "schrieb", "geschrieben", "sehen"

For i = 0 To Max
  ;
Read Wort$(i)
Next
; Zufallsschlaufe 
    SeedRnd MilliSecs() 
    Zufall$ = Wort$(Rand(0,Max))

Geraten$ = String$("?",Len(Zufall$))

WaitKey()

SetFont Arial_50
StartZeit = MilliSecs()
Cls
Locate 0,0
DrawBlock SElba, 0,0
Versuche=0
VersucheV=10
GeratenA$=0
Repeat
If GeratenA$=Geraten$ Then Versuche=Versuche+1
VersucheV=10-Versuche
GeratenA$=Geraten$
Repeat
Print ""
Print "	"+Geraten$
If Not SGBS$="" Then Print "	Geratene Buchstaben: "+ Mid$(SGBS$,3,Len (SGBS$))
Text 1,840,"	Du hast noch "+VersucheV+" Versuche!"
;Print "	Buchstaben: "
Zeichen$ = Lower$(Chr$(WaitKey()))
;Zeichen$ = Input("	Buchstaben: ")
Until Len (Zeichen$)=1
If SGBS$="" Then SGBS$=SGBS$+Zeichen$ Else SGBS$=SGBS$+","+Zeichen$
Cls
Locate 0,0
DrawBlock SElba, 0,0
  ; Prüfen auch auf Mehrfachvorkommen
  For i = 1 To Len(Zufall$)
    If Zeichen$ = Mid$(Zufall$,i,1) Then
      Stelle = i
      Neu$ = Left$(Geraten$,Stelle-1) + Zeichen$
      Neu$ = Neu$ + Mid$(Geraten$,Stelle+1)
      Geraten$ = Neu$
    EndIf
  Next
Until Geraten$ = Zufall$ Or VersucheV=1


Aufgabe_abgebrochen=0 : SpielstandSLR


FlushKeys

ClsVB
DrawBlock SElba, 0,0
SetFont Arial_50

Text 640,840,"-Weiter mit beliebiger Taste-",True

If Geraten$ = Zufall$ Then
	Text 640,50,"	Herzlichen Glückwunsch!",True
	Text 640,100,"	Du hast das Ziel Erreicht!",True
	Text 640,150,"	Du hast das Wort in "+Versuche+" Versuchen erraten!",True
	SpielstandSN("Versuche: "+Versuche)
	Gosub PZeit
	WaitKey()
Else
	Text 640,50,"Das Wort wäre "+Chr$(34)+Zufall$+Chr$(34)+" gewesen!",True
	Text 640,150,"Du hast das Wort leider nicht in 10 Versuchen erraten!",True
	Text 640,200,"Ziel leider nicht erreicht!",True
	Text 640,250,"Versuche die Aufgabe erneut.",True
WaitKey()

SpielstandSN("Zu viele Fehler!")

NAWH_Image=SElba
Gosub NAWH

Goto Aufgabe6
EndIf

If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
FlushKeys
FlushMouse
Goto Programmstart
End
Return

















.Aufgabe7
ClsVB
SpielstandSN("Verben")
Aufgabe_abgebrochen=1 : SpielstandSLR
NVAorP=2
NAWH_Image=Flugzeug
PauseChannel HGM
StartZeit = MilliSecs()
FlushKeys
Fehler=0

Cls
Locate 0,0

DrawBlock Flugzeug, 0,0
SetFont Arial_20
Print ""
SetFont Arial_100
Print " Verben"
SetFont Arial_40
Print ""
Print "	Löse die vollgenden Aufgaben."
Print "	Die Aufgabe gibt maximagl 18 Belohnungsmünzen."
Print "	für jede falsche Aufgabe gibt es zwei Abzug."
Print "	Je nach Schwierigkeitsstufe wird aber nach"
Print "	einer bestimmten Anzahl Fehler die Aufgabe"
Print "	abgebrochen und du musst von vorne beginnen."
Print "	Viel Spass!"
Print ""
Print "	-Weiter mit beliebiger Taste-"

WaitKey()

DrawBlock Flugzeug, 0,0

SetFont Arial_30
Color 0,0,0
Locate 0,20

Print "	Erkenne das Verb in dem folgenden Satz."
Print "	Schreibe das Verb ab und drücke Enter"
Print
Print "	Der Kluge fährt im Zug."
Ueberpruefewort$ = "fährt"
Gosub Grammatik_Ueberpruefung

Print "	Wie heisst das Verb in der Grundform?"
Ueberpruefewort$ = "fahren"
Gosub Grammatik_Ueberpruefung

Print "	Und wie heisst das Verb im Präteritum (ich-Form z.B. redete)?"
Ueberpruefewort$ = "fuhr"
Gosub Grammatik_Ueberpruefung
Delay 1500



Locate 0,20
DrawBlock Flugzeug, 0,0
Fehler = 0
Print "	Erkenne das Verb in dem folgenden Satz."
Print "	Schreibe das Verb ab und drücke Enter"
Print
Print "	Zeichne die Linien mit dem Lineal."
Ueberpruefewort$ = "zeichne"
Gosub Grammatik_Ueberpruefung

Print "	Wie heisst das Verb in der Grundform?"
Ueberpruefewort$ = "zeichnen"
Gosub Grammatik_Ueberpruefung

Print "	Und wie heisst das Verb im Präteritum (ich-Form z.B. redete)?"
Ueberpruefewort$ = "zeichnete"
Gosub Grammatik_Ueberpruefung
Delay 1500



Locate 0,20
DrawBlock Flugzeug, 0,0
Fehler = 0
Print "	Erkenne das Verb in dem folgenden Satz."
Print "	Schreibe das Verb ab und drücke Enter"
Print
Print "	Kennst du einen guten Witz?"
Ueberpruefewort$ = "kennst"
Gosub Grammatik_Ueberpruefung

Print "	Wie heisst das Verb in der Grundform?"
Ueberpruefewort$ = "kennen"
Gosub Grammatik_Ueberpruefung

Print "	Und wie heisst das Verb im Präteritum (du-Form z.B. redetest)?"
Ueberpruefewort$ = "kanntest"
Gosub Grammatik_Ueberpruefung
Delay 1500



Locate 0,20
DrawBlock Flugzeug, 0,0
Fehler = 0
Print "	Erkenne das Verb in dem folgenden Satz."
Print "	Schreibe das Verb ab und drücke Enter"
Print
Print "	Mein Bruder heisst Anton."
Ueberpruefewort$ = "heisst"
Gosub Grammatik_Ueberpruefung

Print "	Wie heisst das Verb in der Grundform?"
Ueberpruefewort$ = "heissen"
Gosub Grammatik_Ueberpruefung

Print "	Und wie heisst das Verb im Präteritum (er-Form z.B. redete)?"
Ueberpruefewort$ = "hiess"
Gosub Grammatik_Ueberpruefung
Delay 1500



;Dise Aufgabe ergiebt doppelta Anzahl Belohnungsmünzen!
Locate 0,20
DrawBlock Flugzeug, 0,0
Fehler = 0
Print "	Erkenne das Verb in dem folgenden Satz."
Print "	Schreibe das Verb ab und drücke Enter"
Print "	Denke bei diesem Satz an den Verbzusatz."
Print "	Er wird er mit einem Abstand hinter das"
Print "	Stammverb gehängt. z.B fängt auf"
Print
Print "	Die Schule fängt um acht Uhr an."
Ueberpruefewort$ = "fängt an"
Gosub Grammatik_Ueberpruefung

Print "	Wie heisst das Verb in der Grundform?"
Print "	Achte darauf, dass jetzt der Verbzusatz"
Print "	als Suffix (Vorsilbe) dem Verb angehängt wird."
Print "	z.B. auffallen"
Ueberpruefewort$ = "anfangen"
Gosub Grammatik_Ueberpruefung

Print "	Und wie heisst das Verb im Präteritum (ich-Form z.B. sprach an)?"
Print "	Auch hier ist der Verbzusatz ein Suffix."
Ueberpruefewort$ = "fing an"
Gosub Grammatik_Ueberpruefung
Delay 1500


Belohnungsmuenzen=Belohnungsmuenzen+18 : SpielstandSLR

Aufgabe_abgebrochen=0 : SpielstandSLR
Gosub PZeit
If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
Goto Programmstart
End








.Aufgabe8
ClsVB

SpielstandSN("Zeitverständnis")
Aufgabe_abgebrochen=1 : SpielstandSLR

NAWH_Image=SElba

StopChannel HGM

Cls 
Locate 0,20
Color 0,0,0
TileBlock Aufgabenstellung_Hintergrund
SetFont Arial_100
Print " Zeitverständnis"
SetFont Arial_30
Print ""
Print "	Nachdem du Enter drückst hörst du ein Lied."
Print "	Nach einem Liedteil musst du dei anz. Sekunden des Teils in das Textfeld nottieren."
Print "	Mit einem Druck auf Enter erscheint unter dem Textfeld Richtig oder Falsch."
Print "	Mit einem weiteren Druck auf Enter hörst du dann wieder ein Lied."
Print "	So geht es noch 15 mal weiter bis du dann schliesslich das Ende des Spiels erreicht hast."
Print ""
Print "	Achtung!"
Print "	Genau die richtige Sekundenangabe geben zwei Punkte, eine Sekunde daneben noch einen Punkt."
Print "	Am Ende der Aufgabe, gibt es für jeden Punkt eine halbe Belohnungsmünze."
Print "	Also, beträgt die maximale Anzahl Behlonungsmünzen dieser Aufgabe 15."
Print "	Fals es eine Kommazahl an Belohnungsmünzen gibt wird sie aufgerundet! :D"
Print "	Alle Zeiten sind zwischen 1 und 10 Sekunden! Keine ist über 10!"
Print "	Du musst mindestens 10/30 Punkte haben, um weiterzukommen!"
Print ""
Print "	Tipp:"
Print "	Zähle langsam mit. oder zähle normal 1 und 2 und 3 und..."
Print "	mit der Zeit bekommst du richtig Übung darin. Wiederhole die Aufgabe doch"
Print "	auch freiwillig noch einige Male, wenn du dich noch nicht so sicher fühlst."
Print "	Als letztes, bitte ich dich, nicht mit einer Uhr zu mogeln, denn so lernst du nichts."
Print "	du bei dieser Übung wirklich überhaubt nichts."
Print "	Viel Spass!"
Print ""
Print "	-Weiter mit beliebiger Taste-"
Game=LoadSound (".\Sounds\Harfe 3.mp3")
LoopSound(Game)
FlushKeys
WaitKey()
ClsColor 0,0,0
Cls
DrawBlock Gletscher,0,0
SetFont Arial_40
RichtigLZ=0

GameP=PlaySound(Game)


Zeitver(1)
Zeitver(3)
Zeitver(5)
Zeitver(5)
Zeitver(8)
Zeitver(10)
Zeitver(8)
Zeitver(3)
Zeitver(2)
Zeitver(6)
Zeitver(5)
Zeitver(9)
Zeitver(3)
Zeitver(8)
Zeitver(7)
Delay 1500

FreeSound(Game)


Aufgabe_abgebrochen=0
Belohnungsmuenzen=Belohnungsmuenzen+Ceil#(0.5*RichtigLZ)
SpielstandSLR



TileBlock Aufgabenstellung_Hintergrund
Locate 0,20


;21
SetFont Arial_100
Print " Auswertung"

SetFont Arial_35

Print ""
Print "	Du hast "+RichtigLZ+"/30 Punkte erreicht!"

If RichtigLZ>20 Then
Print "	Herzlichen Glückwunsch!"
Print "	Du warst spitze!"
Print "	Du hast ein sehr gutes Zeitverständnis"
Print "	Ich empfehle dir keine weitere Zeit mit dieser Übung mehr zu verbringen!"
ElseIf RichtigLZ>10
Print "	Du hast das Ziel zwar erreicht, deine"
Print "	Leistungen sind jedoch immer noch zu verbessern!"
Print "	Du solltest diese Aufgabe später nochmals wiederholen,"
Print "	musst aber nicht!"
Else
Print "	Du hast das Ziel leider nicht erreicht!"
Print "	Wiederhole doch diese Aufgabe noch einige Male,"
Print "	um dein Zeitverständnis immerhin in einen guten Bereich zu bringen."
Print "	Wenn es nicht klappt versuche doch Mal mit einer Uhr im Sekundentakt"
Print "	zu zählen und danach ohne Uhr genau gleich die Zeiten dieser Übung abzuzählen!"
EndIf

Text 0,950,"	-Weiter mit beliebeger Taste-"


SpielstandSN("Punkte: "+RichtigLZ+"/30")
WaitKey()

If RichtigLZ>=10 Then
	If UebersichtA=1 Then Goto Uebersicht3
	Aufgaben=Aufgaben+1
	Goto Programmstart
EndIf
Gosub NAWH
Goto Aufgabe8
End





Function Zeitver(ZfZeit)
Delay ZfZeit*1000
PauseChannel Gamep
Cls
DrawBlock Gletscher,0,0
Text 790,50,"Sekunden"
Locate 450,50
FlushKeys
;LiedZ=Input ("Das Lied dauerte ")

If LiedZ=ZfZeit Then
	Color 0,255,0
	Text 640,100,"Richtig 2P"
	RichtigLZ=RichtigLZ+2
ElseIf LiedZ=ZfZeit-1 Or LiedZ=ZfZeit-1
	Color 255,190,0
	Text 640,100,"Fast Richtig 1P"
	RichtigLZ=RichtigLZ+1
Else
	Color 255,0,0
	Text 640,100,"Falsch 0P"
EndIf

Color 0,0,0
ResumeChannel GameP
End Function









.Aufgabe9
SpielstandSN("Witze")
Aufgabe_abgebrochen=1 : SpielstandSLR
PauseChannel HGM
ClsVB
Witze=0

Color 0,0,0

TileBlock Aufgabenstellung_Hintergrund
Locate 0,20

SetFont Arial_100
Print " Witze"
SetFont Arial_35
Print ""
Print "	Diese Aufgabe ist zum Entspannen gedacht."
Print "	Mit beliebiger Taste erscheint der erste Witz."
Print "	Auf die gleiche Weise kommst dü übrigens auch immer zum nächsten."
Print "	Insgasamt werden 10/30 Witze zufällig ausgewählt."
Print ""
Print "	Die ganze Aufgabe ergibt 10 Belohnungsmünzen!"
Print "	Um diese Aufgabe vor Münzensammelbetrug zu schützen, werden"
Print "	die Münzen nur einmal pro Programmstart hinzugerechnet."
Print "	ausserdem wird ein allzu schnelles Hindurchklicken der Aufgabe, durch"
Print "	Wartebefehle die das Springen zum nächsten Witz erst nach dem Ablauf"
Print "	der anz. Zeilen  * 1 Sekunde erlaubt, verhindert. Falls du ein Witz also"
Print "	schon kennst, warte einfach ein paar Sekunden. In der Zeit, kannst du"
Print "	z.B. ausrechnen wie lange du eigentlich genau warten musst! :D :D :D"
Print "	Die Zeit ist übrigens um, wenn du unten die Info "
Print "	"+Chr$(34)+"-Weiter mit beliebiger Taste-"+Chr$(34)+" siehst.
Print ""
Print "	-Weiter mit beliebiger Taste-"
FlushKeys
WaitKey()

ClsColor 255,0,0
Cls
Color 0,0,0
Locate 0,20

Aufgabe=0
NZDGW(0)=0
NZDGW(1)=0
NZDGW(2)=0
NZDGW(3)=0
NZDGW(4)=0
NZDGW(5)=0
NZDGW(6)=0
NZDGW(7)=0
NZDGW(8)=0
NZDGW(9)=0
NZDGW(10)=0

StartZeit = MilliSecs()

SeedRnd MilliSecs()

Repeat
Zufall=Rand(1,30)
VDNZGWK=0
Repeat
VDNZGWK=VDNZGWK+1
If NZDGW(VDNZGWK)=Zufall Then Zufall=Rand(1,30) VDNZGWK=0
Until VDNZGWK=10

ClsColor 255,201,14
Cls

If Zufall=1 Then
Print "  Die Familie macht einen Ausflug mit dem"
Print "  Zug in die Berge. Auf dem Bahnhof in München"
Print "  hat der Zug etwas länger Aufenthalt."
Print "  Auf dem Bahnsteig geht ein Mann"
Print "  mit einem Imbisswagen entlang und ruft laut:"
Print "  "+Chr$(34)+"Heisse Würstchen! Heisse Würstchen!"+Chr$(34)
Print "  Da öffnet Paul das Fenster und ruft hinaus:"
Print "  "+Chr$(34)+"Könnten Sie nicht still sein? Uns"
Print "  interessiert es nicht, wie Sie heissen."+Chr$(34)
Delay 9000
EndIf

If Zufall=2 Then
Print "  Ostfriesische Klassenfahrt: Ein ostfriesischer"
Print "  Lehrer wartet mit seinen Schülern der 3. Klasse"
Print "  auf dem Bahnsteig. Einen Zug nach dem anderen"
Print "  lässt er passieren, ohne mit seiner Klasse"
Print "  einzusteigen. Schliesslich platzt ihm der Kragen:"
Print "  "+Chr$(34)+"Den nächsten nehmen wir, Kinder. Auch wenn wieder"
Print "  nur 1. und 2. Klasse draufsteht."+Chr$(34)
Delay 7000
EndIf

If Zufall=3 Then
Print "  Abschlussprüfung auf der Polizeiakademie."
Print "  Die Anwärter werden einzeln der Prüfungskommission"
Print "  vorgeführt. Den ersten fragt der Prüfer: "+Chr$(34)+"Was ist schneller,"
Print "  Licht oder Schall?"+Chr$(34)+" "+Chr$(34)+"Schall! Warum denn das,
Print "  fragt der Vorsitzende?"+Chr$(34)
Print "  "+Chr$(34)+"Nun, wenn ich meinen Fernseher anmache, kommt auch "
Print "  erst der Ton, und ...."+Chr$(34)+" "+Chr$(34)+"Durchgefallen! der Nächste"
Print "  bitte. Was ist schneller, Licht oder Schall?"+Chr$(34)+""
Print "  "+Chr$(34)+"Licht!"+Chr$(34)+" "+Chr$(34)+"Und warum?"+Chr$(34)+" "+Chr$(34)+"Wenn ich mein Radio anmache"
Print "  geht auch zuerst das Licht und ..."+Chr$(34)+" "+Chr$(34)+"Durchgefallen,"
Print "  raus. Der Nächste, bitte. "+Chr$(34)+"Was ist schneller Licht"
Print "  oder Schall?"+Chr$(34)+" "+Chr$(34)+"Licht, ist doch klar!"+Chr$(34)+" "+Chr$(34)+"Aha. Und warum?"+Chr$(34)
Print "  "+Chr$(34)+"Nun, die Augen sind doch am Kopf viel weiter"
Print "  vorn als die Ohren..."+Chr$(34)+""
Delay 14000
EndIf

If Zufall=4 Then
Print "  Der Lehrer: "+Chr$(34)+"Lukas, warum kommst du schon"
Print "  wieder zu spät?"+Chr$(34)
Print "  "+Chr$(34)+"Ich habe von einem Fussballspiel geträumt."+Chr$(34)
Print "  "+Chr$(34)+"Das ist doch kein Grund, zu spät zukommen!"+Chr$(34)
Print "  "+Chr$(34)+"Doch! Es gab Verlängerung!"+Chr$(34)
Delay 5000
EndIf

If Zufall=5 Then
Print "  "+Chr$(34)+"Hören Sie mal zu"+Chr$(34)+", sagt der Polizist zum Golfspieler,"
Print "  "+Chr$(34)+"ihr Ball ist auf die Strasse geflogen und hat"
Print "  dort die Frontscheibe eines Feuerwehrwagens im"
Print "  Einsatz zertrümmert, der deswegen gegen eine "
Print "  Mauer geprallt ist. Das Haus, das gelöscht werden"
Print "  sollte ist bis auf die Grundmauer niedergebrannt"
Print "  Was haben Sie zu diesem Schlamassel zu sagen?"+Chr$(34)
Print "  "+Chr$(34)+"Golfer: Wo ist mein Golfball!"+Chr$(34)
Delay 8000
EndIf

If Zufall=6 Then
Print "  Mäxchen sitzt auf dem Brunnenrand und heult."
Print "  Kommt ein Polizist dazu und fragt, was los sei."
Print "  "+Chr$(34)+"Meine Muter ist in den Brunnen gefallen"+Chr$(34)+", schluchst"
Print "  Mäxchen. Der Polizist zieht sich sofort Uniform"
Print "  und Schuhe aus und stürzt sich in den Brunnen."
Print "  Nach einigen Minuten kommt er wieder heraus, hat"
Print "  aber die Mutter nicht gefunden. Sagt das Mäxchen:"
Print "  "+Chr$(34)+"Na toll, dann kann ich ja die blöde Schraube"
Print "  auch wegwerfen!"+Chr$(34)
Delay 9000
EndIf

If Zufall=7 Then
Print "  Ein Mann sitzt in der Kneipe und bestellt sich"
Print "  ein Bier. Der Kellner bringt dem bereits ziemlich"
Print "  betrunkenen Mann das Bier und stellt es auf einen"
Print "  Bierdeckel auf den Tisch. Nach 10 Minuten bestellt"
Print "  der Mann wieder ein Bier. Der Kellner bringt es"
Print "  aber der Bierdeckel ist weg, also legt er einen"
Print "  neuen unters Bier. Nach weiteren 10 Minuten bestellt"
Print "  der Mann wieder ein Bier. Wieder ist der Bierdeckel"
Print "  weg. Der Kellner legt wieder einen neuen unter das"
Print "  Bier. Schliesslich bestellt der Mann das 3.Mal ein"
Print "  Bier, aber wieder ist der Bierdeckel weg. Der"
Print "  Kellner hat die Schanauze voll und legt keinen"
Print "  neuen unters Bier. Beschwert sich der Mann:"
Print "  "+Chr$(34)+"Wo bleibt der Keks?"+Chr$(34)
Delay 14000
EndIf

If Zufall=8 Then
Print "  Schön, dass du gekommen bist, Tante Ottilie, sagte"
Print "  Florian artig. Gestern erst hat Papa gesagt:"
Print "  "+Chr$(34)+"Tante Ottilie fehlt uns gerade noch!"+Chr$(34)
Delay 3000
EndIf

If Zufall=9 Then
Print "  Mama, darf ich ein bisschen mit Opa spielen?"
Print "  Nein der Sarg bleibt heute zu."
Delay 2000
EndIf

If Zufall=10 Then
Print "  Polizist: "+Chr$(34)+"Herzlichen Glückwunsch. Sie sind"
Print "  der 100 000ste Autofahrer, der diese neue"
Print "  Autobahn befährt, und Sie bekommen als Preis"
Print "  20 000 Euro! Was möchten Sie mit diesem Geld"
Print "  anfangen?"+Chr$(34)+" Vater: "+Chr$(34)+"Dann mach ich mir mal zuerst"
Print "  den Führerschein."+Chr$(34)+" Mutter: "+Chr$(34)+"Hören Sie nicht auf"
Print "  ihn, er ist total betrunken."+Chr$(34)+" Der schwerhörige"
Print "  Opa: "+Chr$(34)+"Ich habe euch doch gleich gesagt, dass"
Print "  es keine gute Idee war, das Auto zu stehlen."+Chr$(34)+""
Print "  Eine Stimme aus dem Kofferraum: "+Chr$(34)+"Sind wir "
Print "  schon hinter der Grenze?"+Chr$(34)
Delay 11000
EndIf

If Zufall=11 Then
Print "  Fragt der Lehrer: "+Chr$(34)+"Wer kann mir Tiere nennen,"
Print "  die bei uns leben?"+Chr$(34)+""
Print "  Mariechen streckt den Finger und zählt auf:"
Print "  "+Chr$(34)+"Eselchen,Pferdchen,Schäfchen..."+Chr$(34)
Print "  Der Lehrer unterbricht: Lass doch bitte das"
Print "  >chen< weg!"
Print "  Mariechen fährt fort: "+Chr$(34)+"Kanin, Eichhorn..."+Chr$(34)+""
Delay 7000
EndIf

If Zufall=12 Then
Print "  Fritzchen geht mit seiner Oma in die Stadt."
Print "  Vor dem Supermarkt findet er eine Zehnfrankennote."
Print "  Als er sie aufheben will, sagt die Oma: "+Chr$(34)+"Was auf"
Print "  dem Boden liegt, darf man nicht aufheben!"+Chr$(34)
Print "  Sie gehen weiter. Fritzchen findet eine"
Print "  Zwanzigfrankennote. Die Oma sagt wieder: "+Chr$(34)+"Was auf"
Print "  dem Boden liegt, darf man nicht aufheben!"+Chr$(34)+""
Print "  Auf dem Rückweg fällt die Oma hin. Fritzchen,"
Print "  hilf mir doch aufzustehen! Da sagt dieser: "+Chr$(34)+"Was"
Print "  auf dem Boden liegt, darf man nicht aufheben!"+Chr$(34)+""
Delay 10000
EndIf

If Zufall=13 Then
Print "  Ein Amerikaner machte eine Stadtrundfahrt durch"
Print "  Paris und lässt sich die Attraktionen zeigen."
Print "  Am Triumphbogen erzählt ihm der französische"
Print "  Taxifahrer, dass dieser Bogen ein wahres Wunderwerk"
Print "  sei, 20 000t schwer. Der Amerikaner fragt, wie lange"
Print "  der Bau dieses Monuments gedauert habe. Als er"
Print "  erfährt, dass es 15 Jahre gedauert hat, lachte er nur"
Print "  und sagte: "+Chr$(34)+"Ach in Amerika brauchen wir für so etwas"
Print "  höchstens 15 Tage."+Chr$(34)+" Der Franzose ist schon etwas"
Print "  eingeschnappt. Am Louvre angelangt, das gleiche"
Print "  Spiel. Der Franzose nennt die Bauzeit von 20 Jahren."
Print "  Daraufhin behauptet der Amerikaner: "+Chr$(34)+"Ach, in"
Print "  Amerika brauchen wir für sowas höchstens 20 Tage."+Chr$(34)
Print "  Schliesslich kommen sie zum Eiffelturm. Donnerwetter,"
Print "  staunt der Amerikaner, was ist denn das? Der"
Print "  Franzose daraufhin: "+Chr$(34)+"Keine Ahnung, stand gestern"
Print "  noch nicht da!"+Chr$(34)
Delay 17000
EndIf

If Zufall=14 Then
Print "  Das Thema des Deutschaufsatzes lautet: "+Chr$(34)+"Mein"
Print "  Lieblingstier."+Chr$(34)+" Simone schreibt: "+Chr$(34)+"Unser Hund ist"
Print "  super. Er ist der beste Hund der Welt. Er sucht"
Print "  Stöckchen, springt sehr hoch und bringt uns jeden"
Print "  Morgen die Zeitung, obwohl wir gar keine"
Print "  abonniert haben."+Chr$(34)+""
Delay 6000
EndIf

If Zufall=15 Then
Print "  Moritz sagt beim Abendessen zu seinem Vater:"
Print "  "+Chr$(34)+"Vater, ich muss dir was sagen!"+Chr$(34)+" Der Vater:"
Print "  "+Chr$(34)+"Nein, jetzt nicht, Moritz. Man spricht nicht"
Print "  mit vollem Mund."+Chr$(34)+" "+Chr$(34)+" Aber es ist sehr wichtig,"
Print "  Vater!"+Chr$(34)+", sagte Mortiz drängelnd. "+Chr$(34)+"Das kannst"
Print "  du mir auch später sagen!"+Chr$(34)+", antwortet der "
Print "  Vater wütend. Nach dem Essen. "+Chr$(34)+"So, Moritz,"
Print "  was wolltest du mir sagen?"+Chr$(34)+" "+Chr$(34)+"Ach, jetzt ist"
Print "  es zu spät, du hast den Wurm im Salat schon"
Print "  gegessen."+Chr$(34)
Delay 10000
EndIf

If Zufall=16 Then
Print "  Ein Schäfer sitzt mit einem Schaf am Strassenrand."
Print "  Da kommt ein Porsche vorbei, blieb stehen und"
Print "  bietet dem Hirten an mitzufahren. Allerdings"
Print "  nur ohne Schaf. Der Schäfer sagt dem Porschefahrer,"
Print "  dass es kein Problem wäre, das Schaf einfach hinten"
Print "  am Auto anzubinden. Als sie schon 100km/h fahren,"
Print "  sieht der Porschefahrer überrascht, dass das Schaf"
Print "  ganz locker hinter seinem Auto hertrabt.Also"
Print "  beschliesst er, noch etwas schneller zu fahren."
Print "  Bei 150 km/h ist das Schaf schon fast im Galopp."
Print "  Als er weiter auf 200km/h beschleunigt, sieht er,"
Print "  dass das Schaf auf einmal so seltsam mit dem linken"
Print "  Ohr wackelt. Als er den Schäfer darauf aufmerksam"
Print "  macht, sagt dieser nur: "+Chr$(34)+"Keine Sorge, das macht's"
Print "  immer wenn's überholen will!"+Chr$(34)
Delay 15000
EndIf

If Zufall=17 Then 
Print "  Friederich und seine Freunde haben Murmeln gekauft."
Print "  Sie kommen an einer Leichenhalle vorbei. Vor dem"
Print "  Eingang verteilen sie zwei Murmeln, die hineinkullern."
Print "  In der Halle zählten sie die Murmeln ab. "+Chr$(34)+"Eine für"
Print "  dich, eine für mich ..."+Chr$(34)+" usw. Der Hausmeister kommt"
Print "  vorbei, hört das und rennt zum Pfarrer: "+Chr$(34)+"Herr"
Print "  Pfarrer, in der Leichenhalle spielt der Teufel"
Print "  mit Gott um die Seele der Verstorbenen!"+Chr$(34)+" Also"
Print "  geht der Pfarrer mit ihm zurück. Dort hören"
Print "  sie eine Stimme: "+Chr$(34)+"Das waren alle! Und die vor"
Print "  der Tür holen wir uns auch noch!"+Chr$(34)+""
Delay 11000
EndIf

If Zufall=18 Then
Print "  Reisender zum Schaffner:"
Print "  Wie lange hält der Zug?-"
Print "  Bei guter Pflege 25 Jahre."
Delay 3000
EndIf

If Zufall=19 Then
Print "  "+Chr$(34)+"Können Sie mir einen unbekanten,"
Print "  schneesicheren Urlaubsort empfehlen?"+Chr$(34)
Print "  Tut mir leid, die sind alle ausgebucht!"
Delay 3000
EndIf

If Zufall=20 Then
Print "  Ein Mann beim Verhör."
Print "  Polizist: "+Chr$(34)+"Sie haben doch gesehen, wie"
Print "  die alte Dame von einem Kerl verprügelt"
Print "  wurde. Wieso haben Sie dann nicht geholfen?"+Chr$(34)
Print "  Mann: "+Chr$(34)+"Ich dachte, der schafft das allein!"+Chr$(34)
Delay 5000
EndIf

If Zufall=21 Then
Print "  Ein Landwirt gewinnt 3000 Euro im Lotto und"
Print "  bekommt sie in drei 1000 Euro Scheinen bar"
Print "  bezahlt. Leider fällt ihm das Geld auf den"
Print "  Boden und seine fette Sau frisst das Geld."
Print "  Der Geldbote hatte einen Ratschlag parat:"
Print "  "+Chr$(34)+"Geben Sie der Sau ein Korn zu trinken und"
Print "  treten Sie ihr in den Hintern, dann kotzt"
Print "  sie das Geld wieder aus."+Chr$(34)+" Da der Bauer gerade"
Print "  kein Korn zuhause hat, schleppt er die Sau in"
Print "  die nächste Kneipe, bestellt ein Bier und ein"
Print "  Korn. Er trinkt das Bier auf ex, gibt der Sau"
Print "  den Korn,tritt ihr in den Hintern, und siehe da,"
Print "  sie erbricht einen Tausender. Der Wirt ist "
Print "  begeistern und fragt,ob er das Tier kaufen könne."
Print "  "+Chr$(34)+"Die Sau ist unverkäuflich,"+Chr$(34)+" sagte der Bauer,"
Print "  bestellt noch ein Korn, noch ein Bier, tritt"
Print "  der Sau in den Hintern, und der zweite Tausender"
Print "  kommt zum Vorschein. Der Wirt kann es kaum glauben,"
Print "  und der Bauer wiederholte das Spiel zum dritten Mal."
Print "  Darauf der Wirt: "+Chr$(34)+"Ich gebe Ihnen 10'000 Euro in bar"
Print "  für das Tier."+Chr$(34)+" Der Bauer überlegt nicht lange und"
Print "  willigt zufrieden ein. Er lässt die Sau in der "
Print "  Kneipe und geht mit seinen 10'000 Euro heim. Am"
Print "  nächsten Tag liest der Bauer in der Zeitung die"
Print "  Schlagzeilen: "+Chr$(34)+"Ein betrunkener Gastwirt tritt"
Print "  seine Sau tot!!!"+Chr$(34)
Delay 26000
EndIf

If Zufall=22 Then
Print "  "+Chr$(34)+"In diesem Jahr werde ich im Urlaub nichts tun."
Print "  Die erste Woche werde ich mich nur im Schaukelstuhl"
Print "  entspannen."+Chr$(34)+" Ja aber dann?-"+Chr$(34)+"Dann werde ich"
Print "  eventuell ein wenig schaukeln."+Chr$(34)
Delay 4000
EndIf

If Zufall=23 Then
Print "  Ein Polizist steht in der Küche und versucht eine"
Print "  Fischbüchse zu öffnen. Erst reisst er die Lasche ab,"
Print "  dann zerbeult er mit dem Büchsenöffner die "
Print "  Seitenwand. Dann verbeult er den Deckel. Schliesslich"
Print "  nimmt der Polizist seinen Gummiknüppel, haut mehrfach"
Print "  auf die Büchse und schreit: "+Chr$(34)+"Aufmachen, Polizei!"+Chr$(34)
Delay 6000
EndIf

If Zufall=24 Then
Print "  Wegen zu geringer Bildung sollen Leute mit"
Print "  Fremdsprachenkenntnis als Polizisten angeworben"
Print "  werden. Es melden sich auch welche: Prüfer:"
Print "  "+Chr$(34)+"Do you speak English?"+Chr$(34)+" 1.Bewerber:"+Chr$(34)+"Äh?"+Chr$(34)+" Durchgefallen."
Print "  Prüfer: "+Chr$(34)+"Do you speak English?"+Chr$(34)+" 2.Bewerber:"+Chr$(34)+"Äh?"+Chr$(34)
Print "  "+Chr$(34)+"Durchgefallen."+Chr$(34)+" Prüfer: "+Chr$(34)+"Do you speak English?"+Chr$(34)
Print "  3. Bewerber: "+Chr$(34)+"Oh yes, I do."+Chr$(34)+" Prüfer: "+Chr$(34)+"Äh?"+Chr$(34)
Delay 7000
EndIf

If Zufall=25 Then
Print "  Der arbeitslose Maurer muss zur Stellenvermittlung"
Print "  Der Beamte: "+Chr$(34)+"Jetzt habe ich Ihnen schon sieben"
Print "  Baustellen vermittelt, und bei keiner haben Sie"
Print "  angefangen!"+Chr$(34)+" Der Maurer verzweifelt: "+Chr$(34)+"Was soll ich"
Print "  denn machen? Da stand jedesmal auf einem Schild-"
Print "  Baustelle betreten verboten!"+Chr$(34)
Delay 6000
EndIf

If Zufall=26 Then
Print "  Zwei Polizisten auf Streife kommen an dem Haus vorbei,"
Print "  in dem einer von ihnen wohnt. Da zeigt dieser nach oben"
Print "  und meint: "+Chr$(34)+"Da oben wohne ich. Das auf dem Balkon ist"
Print "  meine Frau und der Mann daneben - das bin ich"+Chr$(34)
Delay 4000
EndIf


If Zufall=27 Then
Print "  Schon über eine halbe Stunde verfolgt der Polizist"
Print "  den Dieb. Endlich kann der Dieb nicht mehr und "
Print "  lässt sich auf eine Bank fallen. Schnaufend "
Print "  stoppt der Polizist und setzt sich ebenfalls."
Print "  Nach einer Weile schaut der Dieb zum Polizisten "
Print "  hinüber: "+Chr$(34)+"Na, packen wir es wieder?"+Chr$(34)
Delay 6000
EndIf

If Zufall=28 Then
Print "  Schimpft der Polizist mit dem Passanten:"
Print "  "+Chr$(34)+"Sie dürfen doch nicht bei Rot über die Strasse gehen."
Print "  Sind Sie denn von Sinnen?"+Chr$(34)+""
Print "  "+Chr$(34)+"Nein"+Chr$(34)+", Herr Polizist, "+Chr$(34)+"von Zürich!"+Chr$(34)
Delay 4000
EndIf

If Zufall=29 Then
Print "  Der Richter vorwurfsvoll zum Angeklagten:"
Print "  Sie haben in dem Hotel Handtücher geklaut."
Print "  Wissen Sie, was darauf steht?"
Print "  Natürlich: "+Chr$(34)+"Hotel zum Bären"+Chr$(34)
Delay 4000
EndIf

If Zufall=30 Then
Print "  Der Polizist zum Autofahrer:"
Print "  "+Chr$(34)+"Was fällt Ihnen ein, in der Stadt"
Print "  70 Kilometer in der Stunde zu fahren?"+Chr$(34)
Print "  "+Chr$(34)+"Unmöglich, ich Bin ja erst zehn Minuten unterwegs!"+Chr$(34)
Delay 4000
EndIf

;ClsColor 255,255,255
;Input("  ")
Text 0,970,"  -Weiter mit beliebiger Taste-"
FlushKeys
WaitKey()
ClsColor 255,0,0
Cls
Locate 0,20
Witze=Witze+1
NZDGW(Witze)=Zufall
Until Witze = 10
;Spielstandsicherung
FlushKeys

Aufgabe_abgebrochen=0 : SpielstandSLR

Gosub PZeit

If SHMWBVW=0 Then
	Belohnungsmuenzen=Belohnungsmuenzen+10
	SpielstandSLR
	SHMWBVW=1
EndIf


If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=9
Goto Programmstart
End








.Aufgabe10

Dim Aufgabe10_Woerter$(50)

ClsVB
SpielstandSN("Artikel")
Aufgabe_abgebrochen=1 : SpielstandSLR
PauseChannel HGM
Aufgabe10_Richtig=0

Color 0,0,0

TileBlock Aufgabenstellung_Hintergrund
Locate 0,20

SetFont Arial_100
Print " Artikel"
SetFont Arial_50
Print ""
Print "	Bei dieser Aufgabe muss man den Artikel anklicken der"
Print "	zum oben stehenden Nomen passt."
Print "	Bei dieser Aufgabe musst du mindestens 16/20 Aufgaben"
Print "	richtig haben, sonst musst du die Aufgabe wiederholen."
Print "	Jede richtig gelöste Aufgabe gibt übrigens je eine"
Print "	Behlonungsmünze"
Print ""
Print "	-Weiter mit beliebiger Taste-"
FlushKeys
WaitKey()

ClsColor 255,0,0
Cls


.Aufgabe10_Dateienauswahl

ClsColor 0,0,0
Cls
Locate 0,20

TileBlock Aufgabenstellung_Hintergrund
SetFont Arial_100
Print " Wortdateienauswahl"
SetFont Arial_40
Print ""
Print "	Wähle eine Artikeldatei aus, indem du ihre Nummer eingibst und mit Enter"
Print "	bestätigst. Einige Wortdatien sind beigelegt. Es können aber neue  hinzu-"
Print "	gefügt oder die beigelegten bearbeitet werden. Siehe Bedinungsanleitung!"
Print ""
Write "	Folgende Wortdateien sind verfügbar:	"


Aufgabe_Dateienindex_Zaeler=0
Aufgabe_WortdateieNr=1

Aufgabe_Zeilen_Y=400
Aufgabe_Zeilen_X=25

Text Aufgabe_Zeilen_X, Aufgabe_Zeilen_Y, "Hauptordner"

Color 75,75,75

Ortner_tree(".\Data\Artikel\")

SetFont Arial_40
Color 0,0,0

Aufgabe10_Dateinummer_Input=Input("			Dateinummer: ") ;Aufgabe2_Dateinummer_Input muss nicht auf null gesetzt werden, da sie von der Eingabe überschrieben wird.

Cls
Locate 0,0
TileBlock Aufgabenstellung_Hintergrund

Aufgabe10_Woerter_Einlesezaehler=0

Datei$=""

If Not Aufgabe10_Dateinummer_Input=0 Then Datei$=Aufgabe_Dateienindex$(Aufgabe10_Dateinummer_Input-1) ;Wenn Datei$=0 ist dann Datei$=""=Fehlermeldung anzeigen



If Not FileType (Datei$)=1 Then
		Locate 0,20
		SetFont Arial_100
		Print " Fehler:"
		SetFont Arial_40
		Print ""
		Print "	Datei existiert nicht!"
		Print "	Bitte vergewissere dich, dass du wirklich die korrekte"
		Print "	Dateinummer angegeben hast und versuche es erneut!"
		Print ""
		Print "	-Zürück zur Dateienauswahl mit beliebiger Taste-"
		WaitKey()
		Goto Aufgabe10_Dateienauswahl
EndIf


DateiID = ReadFile(Datei$)
While Not Eof(DateiID)
	Aufgabe10_Eingelesene_Zeile$=ReadLine$(DateiID)
	If Not Left$(Aufgabe10_Eingelesene_Zeile$,1)=";" Then
	If Not Len(Aufgabe10_Eingelesene_Zeile$)=0 Then
		If Len(Aufgabe10_Eingelesene_Zeile$)<2 Or Len(Aufgabe10_Eingelesene_Zeile$)>50 Or Instr(Aufgabe10_Eingelesene_Zeile$,"=")=0 Then
			Locate 0,20
			SetFont Arial_100
			Print "  Fehler:"
			SetFont Arial_40
			Print ""
			Print "		Zeile "+(Aufgabe10_Woerter_Einlesezaehler+1)+" ist ungültig!"
			Print "		Wort muss zwischen 2 und 50 Zeichen enthalten!"
			Print "		Zusätzlich muss jede Zeile ein [Artikelwort] = [Artikel Nr.] enthalten!"
			Print "		Bitte Lade eiune korrekte Datei oder repariere sie!"
			Print "		Siehe Bedinungsanleitung!"
			Print ""
			Print "		-Zürück zur Dateienauswahl mit beliebiger Taste-"
			Print ""
			Print ""
			Print ""
			Print "		Zeile "+(Aufgabe10_Woerter_Einlesezaehler+1)+" = "+Chr$(34)+Aufgabe10_Eingelesene_Zeile$+Chr$(34)
			WaitKey()
			Goto Aufgabe10_Dateienauswahl
		EndIf
		If Aufgabe10_Woerter_Einlesezaehler=50 Then
				Locate 0,20
				SetFont Arial_100
				Print " Warnung:"
				SetFont Arial_40
				Print ""
				Print "	Die Datei enthält mehr als 50 gültige Wörter!"
				Print "	Für diese Aufgabe werden einfach nur die ersten"
				Print "	50 verwendet!"
				Print "	Sonst wären es zu viele Aufgaben!"
				Print ""
				Print "	-Weiter mit beliebiger Taste-"
				WaitKey()
				Exit
			EndIf

		Aufgabe10_Woerter$(Aufgabe10_Woerter_Einlesezaehler)=Aufgabe10_Eingelesene_Zeile$ ;Achtung: Nullbasierend!
		;Print Aufgabe2_Woerter$(Aufgabe2_Woerter_Einlesezaehler)
		Aufgabe10_Woerter_Einlesezaehler=Aufgabe10_Woerter_Einlesezaehler+1
		;Input()
	EndIf
	EndIf
	
Wend

If Aufgabe10_Woerter_Einlesezaehler<19 Then
	Locate 0,20
	SetFont Arial_100
	Print " Fehler:"
	SetFont Arial_40
	Print ""
	Print "	Die Datei enthält nur "+Aufgabe10_Woerter_Einlesezaehler+" gültige Wörter!"
	Print "	Für diese Aufgabe werden aber mindestens 20 benötigt"
	Print "	Bitte Lade eine korrekte Datei oder füge noch"
	Print "	einige neue Wörter hinzu!"
	Print "	Siehe Bedinungsanleitung!"
	Print ""
	Print "	-Zürück zur Dateienauswahl mit beliebiger Taste-"
	WaitKey()
	Goto Aufgabe10_Dateienauswahl
EndIf


Cls
Locate 0,0
ArtikelK1=LoadImage (".\Bilder\ArtikelK1.jpg")
ArtikelK2=LoadImage (".\Bilder\ArtikelK2.jpg")
ArtikelK3=LoadImage (".\Bilder\ArtikelK3.jpg")
ArtikelK1O=LoadImage (".\Bilder\ArtikelK1O.jpg")
ArtikelK2O=LoadImage (".\Bilder\ArtikelK2O.jpg")
ArtikelK3O=LoadImage (".\Bilder\ArtikelK3O.jpg")
MaskImage ArtikelK1,255,0,0
MaskImage ArtikelK2,255,0,0
MaskImage ArtikelK3,255,0,0
MaskImage ArtikelK1O,255,0,0
MaskImage ArtikelK2O,255,0,0
MaskImage ArtikelK3O,255,0,0
SetBuffer BackBuffer ()


Color 0,0,0
SeedRnd MilliSecs()


Dim Artikel_Loesung$(4)


For i=0 To Aufgabe10_Woerter_Einlesezaehler-1

	SetFont Arial_90

    Zufall$ = Aufgabe10_Woerter$(i)

	Color 0,0,0

	Repeat
	
		Flip
		Cls
	
		ARTMG=0
		circleX=MouseX()
		circleY=MouseY()
		DrawBlock SElba, 0,0
		Text 640,180,Left$(Zufall$,Instr(Zufall$,"=")-1),True
		If ImageRectOverlap (gfxCircle,circleX,circleY,440,300,400,108) And ARTMG=0 Then DrawImage ArtikelK1O,440,300 ARTMG=1 ArtikelK=1 Else DrawImage ArtikelK1,440,300
		If ImageRectOverlap (gfxCircle,circleX,circleY,440,408,400,108) And ARTMG=0 Then DrawImage ArtikelK2O,440,408 ARTMG=1 ArtikelK=2 Else DrawImage ArtikelK2,440,408
		If ImageRectOverlap (gfxCircle,circleX,circleY,440,516,400,108) And ARTMG=0 Then DrawImage ArtikelK3O,440,516 ARTMG=1 ArtikelK=3 Else DrawImage ArtikelK3,440,516
		DrawImage gfxCircle,circleX,circleY
		
	
	SetBuffer FrontBuffer()
	
	If Aufgabe_10_Falsch_Temp=1 Then
		Color 237,28,36
		Text 640,650,"Falsch",True
		Color 0,0,0
	EndIf



	Until MouseDown(1) And ARTMG=1
		
		
		If ArtikelK=Right$(Zufall$,Len(Zufall$)-Instr(Zufall$,"=")) Then
				Cls
				DrawBlock SElba, 0,0
				Text 640,180,Left$(Zufall$,Instr(Zufall$,"=")-1),True
				If ArtikelK=1 Then DrawImage ArtikelK1O,440,300 Else DrawImage ArtikelK1,440,300
				If ArtikelK=2 Then DrawImage ArtikelK2O,440,408 Else DrawImage ArtikelK2,440,408
				If ArtikelK=3 Then DrawImage ArtikelK3O,440,516 Else DrawImage ArtikelK3,440,516
				AR=1
				Color 0,200,0
				Text 640,650,"Richtig",True
				If Aufgabe_10_Falsch_Temp=0 Then Aufgabe10_Richtig=Aufgabe10_Richtig+1
				Delay 2000
				Exit
			Else
				AR=0
				Color 237,28,36
				Text 640,650,"Falsch",True
				Aufgabe_10_Falsch_Temp=1
				Color 0,0,0
			EndIf


	Aufgabe_10_Falsch_Temp=0

	PPE=0
	ArtikelVRichtig=0

Next



Aufgabe_abgebrochen=0 : SpielstandSLR
SpielstandSN("Punkte: "+Aufgabe10_Richtig+"/"+Aufgabe10_Woerter_Einlesezaehler)
Belohnungsmuenzen=Belohnungsmuenzen+Aufgabe10_Richtig


Cls
Locate 0,20
Color 0,0,0

TileBlock Aufgabenstellung_Hintergrund

SetFont Arial_100
Print " Auswertung"
SetFont Arial_40
Print ""

If Aufgabe10_Richtig<16 Then
	Print "	Du hast leider zu viele Fehler gemacht! Du musst die Aufgabe wiederholen."
	Print "	Versuche doch mal dir die unregelmäsigen Artikel gut einzuprägen."
	Print "	Ohne sie zu merken wirst du nie Fortschritte machen. Wenn du ehrgeizig"
	Print "	bist, solltest du immer andere Artikeldateien auswählen."
Else
	Print "	Helzlichen Glückwunsch!"
	Print "	Du hast das Ziel erreicht."
	Print "	Du kannst dich doch immer noch weiter verbessern, indem du auch noch"
	Print "	andere Artikeldateien auswählst und weiterübst."
	Print ""
	Print "Denke daran:"
	Print "	Die unregelmässigen Artikel musst du einfach auswendig lernen!"
EndIf

Print ""
Print "	-Weiter mit beliebiger Taste-"

WaitKey()

If Aufgabe10_Richtig<16 Then
	SpielstandSN("Zu viele Fehler!")

	NAWH_Image=SElba
	Gosub NAWH
	Goto Aufgabe10
EndIf

If UebersichtA=1 Then Goto Uebersicht3

Aufgaben=10
Goto Programmstart
End










.Aufgabe11
ClsVB
Textverstentnis=0
SpielstandSN("Textverständnis (Der Standhafte Zinnsoldat)")
Aufgabe_abgebrochen=1 : SpielstandSLR
FreeImage HGrundH
HGrundH=LoadImage (".\Bilder\Sonnenuntergang.jpg")
DrawBlock HGrundH, 0,0
PauseChannel HGM

Locate 0,20
SetFont Arial_100
Print " Textverständnis"
SetFont Arial_40
Print ""
Print "	Mit einem Druck auf Enter erscheint ein Text."
Print "	Lese ihn gründlich durch und beantworte dann die folgenden Fragen."
Print "	Da ich nicht alle möglichen Antwortsätze eingeben konnte,"
Print "	habe ich nur Stichworte und kurze Sätze als richtige Lösungen eingegeben."
Print ""
Print "	-Weiter mit beliebiger Taste-"
WaitKey()

Locate 0,20
DrawBlock HGrundH, 0,0

SetFont Arial_60
Print " Der Standhafte Zinnsoldat"

SetFont Arial_30
Print ""
Print "  Es waren einmal fünfundzwanzig Zinnsoldaten, die waren alle Brüder, denn sie waren aus"
Print "  einem alten zinnernen Löffel gemacht worden. Das Gewehr hielten sie"
Print "  im Arm und das Gesicht geradeaus; rot und blau, überaus herrlich war die Uniform; das"
Print "  allererste, was sie in dieser Welt hörten, als der Deckel von der"
Print "  Schachtel genommen wurde, in der sie lagen, war das Wort Zinnsoldaten. Das rief ein"
Print "  kleiner Knabe und klatschte in die Hände; er hatte sie erhalten, denn es" 
Print "  war sein Geburtstag, und er stellte sie nun auf dem Tische auf. Der eine Soldat"
Print "  glich dem andern leibhaft, nur ein einziger war etwas anders; er hatte nur ein Bein,"
Print "  denn er war zuletzt gegossen worden, und da war nicht mehr Zinn genug da; doch stand"
Print "  er ebenso fest auf seinem einen Bein wie die andern auf ihren zweien, und"
Print "  gerade er war es, der sich bemerkbar machte."
Print
Print
Print
Print "  Seite 1 von 7"
WaitKey ()


Locate 0,20
DrawBlock HGrundH, 0,0
Print "  Auf dem Tisch, auf dem sie aufgestellt wurden, stand vieles andere Spielzeug; aber"
Print "  das, was am meisten in die Augen fiel, war ein niedliches Schloss von Papier;"
Print "  durch die kleinen Fenster konnte man gerade in die Säle hineinsehen. Draußen vor ihm"
Print "  standen kleine Bäume rings um einen kleinen Spiegel, der wie ein kleiner"
Print "  See aussehen sollte. Schwäne von Wachs schwammen darauf und spiegelten sich. Das war"
Print "  alles niedlich, aber das niedlichste war doch ein kleines Mädchen, das"
Print "  mitten in der offenen Schlosstür stand; sie war auch aus Papier ausgeschnitten, aber"
Print "  sie hatte ein schönes Kleid und ein kleines, schmales, blaues Band über den"
Print "  Schultern, gerade wie ein Schärpe; mitten in diesem saß ein glänzender Stern, gerade"
Print "  so groß wir ihr Gesicht."
Print "  Das kleine Mädchen streckte seine beiden Arme aus, denn es war eine Tänzerin, und dann"
Print "  hob es das eine Bein so hoch empor, dass der Zinnsoldat es durchaus"
Print "  nicht finden konnte und glaubte, dass es gerade wie er nur ein Bein habe." 
Print " ,Das wäre eine Frau für mich', dachte er, aber sie ist etwas vornehm, sie wohnt in"
Print "  einem Schlosse, ich habe nur eine Schachtel, und da sind wir fünfundzwanzig"
Print "  darin, das ist kein Ort für sie, doch ich muss suchen, Bekanntschaft mit ihr anzuknüpfen!'"
Print
Print
Print
Print "  Seite 2 von 7"
WaitKey ()


Locate 0,20
DrawBlock HGrundH, 0,0
Print "  Und dann legte er sich, so lang er war, hinter eine Schnupftabaksdose,"
Print "  die auf dem Tische stand. Da konnte er recht die kleine, feine Dame betrachten, die fortfuhr"
Print "  auf einem Bein zu stehen, ohne umzufallen." 
Print "  Als es Abend wurde, kamen alle die andern Zinnsoldaten in ihre Schachtel, und die Leute im"
Print "  Hause gingen zu Bette. Nun fing das Spielzeug an zu spielen,"
Print "  sowohl ,Es kommt Besuch!' als auch ,Krieg führen' und ,Ball geben'; die Zinnsoldaten"
Print "  rasselten in der Schachtel, denn sie wollten mit dabei sein, aber sie"
Print "  konnten den Deckel nicht aufheben. Der Nussknacker schoss Purzelbäume, und der Griffel"
Print "  belustigte sich auf der Tafel; es war ein Lärm, dass der Kanarienvogel"
Print "  davon erwachte und anfing mitzusprechen, und zwar in Versen. Die beiden einzigen, die"
Print "  sich nicht von der Stelle bewegten, waren der Zinnsoldat und die Tänzerin;"
Print "  sie hielt sich gerade auf der Zehenspitze und beide Arme ausgestreckt; er war ebenso"
Print "  standhaft auf seinem einen Bein; seine Augen wandte er keinen Augenblick"
Print "  von ihr weg."
Print "  Nun schlug die Uhr zwölf, und klatsch, da sprang der Deckel von der Schnupftabaksdose"
Print "  auf, aber da war kein Tabak darin, nein, sondern ein kleiner, schwarzer"
Print "  Kobold." 
Print "  Das war ein Kunststück!" 
Print "  Zinnsoldat sagte der Kobold, halte deine Augen im Zaum! Aber der Zinnsoldat"
Print
Print
Print
Print "  Seite 3 von 7"
WaitKey ()


Locate 0,20
DrawBlock HGrundH, 0,0
Print "  tat, als ob er es nicht hörte." 
Print "  Ja, warte nur bis morgen! sagte der Kobold." 
Print "  Als es nun Morgen wurde und die Kinder aufstanden, wurde der Zinnsoldat in das"
Print "  Fenster gestellt, und war es nun der Kobold oder der Zugwind, auf einmal flog"
Print "  das Fenster zu, und der Soldat stürzte drei Stockwerke tief hinunter." 
Print "  Das war eine erschreckliche Fahrt. Er streckte das Bein gerade in die Höhe und"
Print "  blieb auf der Helmspitze mit dem Bajonett abwärts zwischen den Pflastersteinen"
Print "  stecken."
Print "  Das Dienstmädchen und der kleine Knabe kamen sogleich hinunter, um zu suchen; aber"
Print "  obgleich sie nahe daran waren, auf ihn zu treten, so konnten sie ihn doch"
Print "  nicht erblicken. Hätte der Zinnsoldat gerufen: Hier Bin ich!, so hätten sie ihn wohl"
Print "  gefunden, aber er fand es nicht passend, laut zu schreien, weil er in Uniform"
Print "  war." 
Print "  Nun fing es an zu regnen; die Tropfen fielen immer dichter, es ward ein ordentlicher"
Print "  Platzregen; als der zu Ende war, kamen zwei Straßenjungen vorbei."
Print "  Sieh du! sagte der eine, da liegt ein Zinnsoldat! Der soll hinaus und segeln!" 
Print "  Sie machten ein Boot aus einer Zeitung, setzten den Soldaten mitten hinein, und nun"
Print "  segelte er den Rinnstein hinunter; beide Knaben liefen nebenher und"
Print "  klatschten in die Hände. Was schlugen da für Wellen in dem Rinnstein, und welcher Strom"
Print
Print
Print
Print "  Seite 4 von 7"
WaitKey ()


Locate 0,20
DrawBlock HGrundH, 0,0
Print "  war da! Ja, der Regen hatte aber auch geströmt. Das Papierboot"
Print "  schaukelte auf und nieder, mitunter drehte es sich so geschwind, dass der"
Print "  Zinnsoldat bebte; aber er blieb standhaft, verzog keine Miene, sah geradeaus und hielt"
Print "  das Gewehr im Arm." 
Print "  Mit einem Male trieb das Boot unter eine lange Rinnsteinbrücke; da wurde es"
Print "  gerade so dunkel, als wäre er in seiner Schachtel." 
Print "  ,Wohin mag ich nun kommen?' dachte er. Ja, Ja, das ist des Kobolds Schuld! Ach, säße"
Print "  doch das kleine Mädchen hier im Boote, da könnte es meinetwegen noch"
Print "  einmal so dunkel sein!' "
Print "  Da kam plötzlich eine große Wasserratte, die unter der Rinnsteinbrücke wohnte. "
Print "  Hast du einen Pass? fragte die Ratte. Her mit dem Passe!" 
Print "  Aber der Zinnsoldat schwieg still und hielt das Gewehr noch fester."
Print "  Das Boot fuhr davon und die Ratte hinterher. Hu, wie fletschte sie die Zähne und"
Print "  rief den Holzspänen und dem Stroh zu: Halt auf! Halt auf! Er hat keinen Zoll"
Print "  bezahlt; er hat den Pass nicht gezeigt!" 
Print "  Aber die Strömung wurde stärker und stärker! Der Zinnsoldat konnte schon da, wo"
Print "  das Brett aufhörte, den hellen Tag erblicken, aber er hörte auch einen"
Print "  brausenden Ton, der wohl einen tapfern Mann erschrecken konnte." 
Print "  Denkt nur, der Rinnstein stürzte, wo die Brücke endete, geradehinaus in einen"
Print "  großen Kanal; das würde für den armen Zinnsoldaten ebenso gefährlich gewesen"
Print "  Nun war er schon so nahe dabei, dass er nicht mehr anhalten konnte. Das Boot fuhr"
Print
Print
Print
Print "  Seite 5 von 7"
WaitKey ()


Locate 0,20
DrawBlock HGrundH, 0,0
Print "  hinaus, der Zinnsoldat hielt sich so steif, wie er konnte; niemand sollte ihm"
Print "  nachsagen, dass er mit den Augen blinke. Das Boot schnurrte drei-, viermal herum und"
Print "  war bis zum Rande mit Wasser gefüllt, es musste sinken. Der Zinnsoldat"
Print "  stand bis zum Halse im Wasser, und tiefer und tiefer sank das Boot, mehr und mehr löste"
Print "  das Papier sich auf; nun ging das Wasser über des Soldaten Kopf. Da"
Print "  dachte er an die kleine, niedliche Tänzerin, die er nie mehr zu Gesicht bekommen"
Print "  sollte, und es klang vor des Zinnsoldaten Ohren das Lied:" 
Print "  ,Fahre, fahre Kriegsmann!"
Print "  Den Tod musst du erleiden!'"
Print "  Nun ging das Papier entzwei, und der Zinnsoldat stürzte hindurch, wurde aber"
Print "  augenblicklich von einem großen Fisch verschlungen." 
Print "  Wie war es dunkel da drinnen!"
Print "  Da war es noch schlimmer als unter der Rinnsteinbrücke, und dann war es so sehr"
Print "  eng; aber der Zinnsoldat war standhaft und lag, so lang er war, mit dem"
Print "  Gewehr im Arm."
Print "  Der Fisch fuhr umher, er machte die allerschrecklichsten Bewegungen; endlich"
Print "  wurde er ganz still, es fuhr wie ein Blitzstrahl durch ihn hin. Das Licht schien ganz"
Print "  klar, und jemand rief laut: Der Zinnsoldat! Der Fisch war gefangen worden, auf den"
Print "  Markt gebracht, verkauft und in die Küche hinaufgekommen, wo die"
Print "  Köchin ihn mit einem großen Messer aufschnitt. Sie nahm mit zwei Fingern den Soldaten"
Print
Print
Print
Print "  Seite 6 von 7"
WaitKey ()


Locate 0,20
DrawBlock HGrundH, 0,0
Print "  mitten um den Leib und trug ihn in die Stube hinein, wo alle den"
Print "  merkwürdigen Mann sehen wollten, der im Magen eines Fisches herumgereist war; aber der"
Print "  Zinnsoldat war gar nicht stolz. Sie stellten ihn auf den Tisch und"
Print "  da - wie sonderbar kann es doch in der Welt zugehen! Der Zinnsoldat war in derselben"
Print "  Stube, in der er früher gewesen war, er sah dieselben Kinder, und das"
Print "  gleiche Spielzeug stand auf dem Tische, das herrliche Schloss mit der"
Print "  niedlichen, kleinen Tänzerin. Die hielt sich noch auf dem einen Bein und hatte das andere"
Print "  hoch in der Luft, sie war auch standhaft. Das rührte den Zinnsoldaten, er war nahe"
Print "  daran, Zinn zu weinen, aber es schickte sich nicht. Er sah sie an, aber sie"
Print "  sagten gar nichts." 
Print "  da nahm der eine der kleinen Knaben den Soldaten und warf ihn gerade in den Ofen,"
Print "  obwohl er gar keinen Grund dafür hatte; es war sicher der Kobold in der"
Print "  Dose, der schuld daran war." 
Print "  Der Zinnsoldat stand ganz beleuchtet da und fühlte eine Hitze, die erschrecklich"
Print "  war; aber ob sie von dem wirklichen Feuer oder von der Liebe herrührte, das"
Print "  wusste er nicht. Die Farben waren ganz von ihm abgegangen - ob das auf der Reise"
Print "  geschehen oder ob der Kummer daran schuld war, konnte niemand sagen."
Print "  Er sah das kleine Mädchen an, sie blickte ihn an, und er fühlte, dass er schmelze,"
Print "  aber noch stand er standhaft mit dem Gewehre im Arm. Da ging eine Tür auf,"
Print "  der Wind ergriff die Tänzerin, und sie flog, einer Sylphide gleich, gerade in den"
Print "  Ofen zum Zinnsoldaten, loderte in Flammen auf und war verschwunden."
Print "  Da schmolz der Zinnsoldat zu einem Klumpen, und als das Mädchen am folgenden Tage"
Print "  die Asche herausnahm, fand sie ihn als ein kleines Zinnherz; von der"
Print "  Tänzerin hingegen war nur der Stern noch da, und der war kohlschwarz gebrannt." 
Print
Print
Print
Print "  Seite 7 von 7"
WaitKey ()



Locate 0,20
DrawBlock SElba, 0,0
Print "	Von welchem Tier wurde der Zinnsoldat verschlungen? (1P) (nur Tiernamen - Keinen Satz! z.B Katze)"
FlushKeys
Zinnsoldat1$ = Input("	")
If Zinnsoldat1$ = "Fisch" Then Print "	Richtig 1P" Textverstentnis=1 Else Print "	Falsch!"
Print ""
Print ""



Print "	Wie viele Zinnsoldaten hatte der Junge in seinem Zinner? (1P) (nur Zahl z.B. 44)"
FlushKeys
Zinnsoldat2$ = Input("	")
If Zinnsoldat2$ = "25" Then Print "	Richtig 1P" Textverstentnis=Textverstentnis+1 Else  Print "	Falsch"
Print ""
Print ""



Print "	Regnete es noch, als die Strassenjungen den Zinsoldaten fanden? (3P)"
FlushKeys
Zinnsoldat3$ = Input("	")
If Lower$(Zinnsoldat3$) = "nein" Then Print"	Richtig 3P" Textverstentnis=Textverstentnis+3 Else Print "	Falsch!"
Print ""
Print ""


Print "	Wie viele Beine hat der Zinnsoldaten der die Hauptperson der Geschichte ist? (1P) (nur Zahl z.B. 44)"
FlushKeys
Zinnsoldat4$ = Input("	")
If Zinnsoldat4$ = "1" Then Print"	Richtig 1P" Textverstentnis=Textverstentnis+1 Else Print "	Falsch!"
Print ""
Print ""


Print "	Wie heisst der Titel dieser Geschichte? (2P) (Gross- Kleinschreibung spielt keine Rolle)"
FlushKeys
Zinnsoldat5$ = Input("	")
If Lower$(Zinnsoldat5$) = "der standhafte zinnsoldat" Then Print"	Richtig 2P" Textverstentnis=Textverstentnis+2 Else Print "	Falsch"
Delay 2500



Aufgabe_abgebrochen=0 : SpielstandSLR
SpielstandSN("Punkte: "+(Textverstentnis)+"/8")


Aufgabe_10_Belohnungsmuenzen=0
DateiID = ReadFile(Name$+".txt")
While Not Eof(DateiID)
If ReadLine$(DateiID)="Textverständnis (Der Standhafte Zinnsoldat)" Then Aufgabe_10_Belohnungsmuenzen=1
Wend
CloseFile DateiID


If Aufgabe_10_Belohnungsmuenzen=0 Then Belohnungsmuenzen=Belohnungsmuenzen+Textverstentnis




Locate 0,0
DrawBlock SElba, 0,0


SetFont Arial_100
Print " Auswertung"
SetFont Arial_35

Print ""

If Textverstentnis<4 Then
	Print "	Du hast leider zu viele Fehler gemacht!"
	Print "	Lese die Geschichte bitte später noch einmal etwas genauer!"
	Print "	Übrigens: Die Fragen sind immer die gleichen! Du kannst deswegen ein"
	Print "	bisschen schummeln, da du beim nächsten Mal ja die Fragen kennst und"
	Print "	deswegen dessen Antworten dir gut merken kannst!"
	Print ""
	Print "	-Weiter mit beliebiger Taste-"
	
	SpielstandSN("Text schlecht verstanden!")
	
	WaitKey()
	
	NAWH_Image=SElba
	Gosub NAWH
	Goto Aufgabe11
Else
	Print "	Herzlichen Glückwunsch!"
	Print "	Du hast das Ziel erreicht!"
	Print ""
	Print "	Übrigens: Ausnahmsweise bringt es nicht sehr viel, diese Aufgabe"
	Print "	zu wiederholen, da es immer den gleichen Text mit den gleichen Fragen ist."
	Print "	Auch ergibt diese Aufgabe nur einmal Belohnungsmünzen, da man sonst schummeln"
	Print "	könnte, indem man den Text gar nicht mehr liest sondern nur die Fragen beantwortet!"
	Print ""
	Print "	-Weiter mit beliebiger Taste-"
	
	SpielstandSN("Text gut verstanden!")
	WaitKey()
EndIf


If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=Aufgaben+1
Goto Programmstart
End







.Aufgabe12
Aufgabe_Aufgabenname$="Minusrechnen"
Goto Aufgabe_1_12_14
End








.Aufgabe13
SpielstandSN("Wortfeld: sagen")
Aufgabe_abgebrochen=1 : SpielstandSLR
PauseChannel HGM

Const MaxWS = 102 ;99

Restore WoerterLWS


Dim Wort$(MaxWS)

For i = 0 To MaxWS
Read Wort$(i)
Next


ClsVB
SYN$=0
i=0
EDVWS=0
WAKY=0
WSR=0
WSF=0
Ziffer=0

TileBlock Aufgabenstellung_Hintergrund

SetFont Arial_100
Color 0,0,0
Locate 0,20
Print " Wortfeld: sagen"
SetFont Arial_40
Print ""
Print "	Du hast drei Minuten Zeit um möglichst viele Synonyme zum Wort"
Print "	"+Chr$(34)+"sagen"+Chr$(34)+" aufzuschreiben z.B. antworten, wiedersprechen, lallen..."
Print ""
Print "	Diese Übung dient dazu, durch Kennen passender Synonyme zum Verb sagen einen"
Print "	Text spannend zu machen, indem du nicht immer das Wort "+Chr$(34)+"sagte"+Chr$(34)+" brauchst."
Print ""
Print "	Du musst mindestens 10 Wörter schaffen um weiter zu kommen!"
Print "	Auch gibt es pro richtiges Wort einen Belohnungspunkt."
Print ""
Print "	Wichtig ist, dass du die Synonyme immer in der Grundform (-en)"
Print "	schreibst. Sonst wird das Wort als falsch ausgewertet. Ein anderes"
Print "	Problem ist, dass bei der Programmierung nicht verhindert werden"
Print "	kann, dass gewisse, sehr seltene Wörter, ebenfalls als falsch"
Print "	ausgewertet werden, da es auch für mich unmöglich war alle möglichen"
Print "	Wörter einzuprogrammieren."
Print "	In der Datenbenk sind übrigens nach dem jetzigen Stand 103 Wörter!"
Print ""
Print "	Viel Glück!"
Print ""
Print "	-Aufgabe starten mit beliebiger Taste-"

BildSagen=LoadImage(".\Bilder\Sagen.jpg")

WaitKey()


;Zeit!
StartZeit = MilliSecs()
Const ZeitMax = 180000  ;180 Sekunden


EGWFSY=100



Dim WS$(99)

Aufgabe_13_WS_Zaeler=0

For i=0 To 100
	Cls
	TileBlock BildSagen
	SetFont Arial_70
	Locate 420,1
	Print "Wortfeld: sagen"
	SetFont Arial_50
	EGWFSY=100

	
	For Zeile=0 To 10
		Locate 420, (Zeile*55)+75
		WS$(Aufgabe_13_WS_Zaeler)=Lower$(Trim$(Input())) ;Eingabe
		If Not WS$(Aufgabe_13_WS_Zaeler)="" Then Aufgabe_13_WS_Zaeler=Aufgabe_13_WS_Zaeler+1
		ZeitJetzt = MilliSecs()
		If (ZeitJetzt-StartZeit > ZeitMax) Then Goto WortfeldSagenV
	Next
Next




.WortfeldSagenV
Cls
HGrundH=LoadImage(".\Bilder\Sonnenuntergang.jpg")
DrawBlock HGrundH, 0,0
SetFont Arial_70
Color 1,1,1
Text 640,1,"Wortfeld: sagen",True
SetFont Arial_55
Text 640,70,"Liste deiner Wörter",True
SetFont Arial_40
Text 640,950,"-Weiter mit beliebiger Taste-",True
FreeFont Schrift
Schrift = LoadFont ("Arial",51,True)
SetFont Schrift
WAKX=250
WAKY=150


;Überprüfung der Eingaben

For EDVWS=0 To 99

	If WS$(EDVWS)="" Then Exit

	Color 255,1,1
	IEDSML=0
	FSWV=0
	
	
	For i=0 To MaxWS
		If WS$(EDVWS)=Wort$(i) Then
			WIEDSML=1
			Color 1,255,1
			WSR=WSR+1
			Exit
		EndIf
	Next


	For FSWV=0 To 99
		If WS$(EDVWS)=WS$(FSWV) And FSWV<>EDVWS And WIEDSML=1 Then
			WSR=WSR-1
			WSG=WES+1
			Color 255,155,55
			Exit
		EndIf
	Next


	If SYN$="" Then WSG=WES-1 WAKX=WAKX
	If WAKY>900 And WAKX=250 Then WAKY=150 WAKX=650
	If WAKY>900 And WAKX=650 Then WAKY=150 WAKX=1030
	WAKY=WAKY+55
	Text WAKX,WAKY,WS$(EDVWS),True

Next


Color 0,0,0

WaitKey()


Cls
HGrundH=LoadImage(".\Bilder\Sonnenuntergang.jpg")
DrawBlock HGrundH, 0,0
SetFont Arial_70
Color 0,0,0
Text 640,1,"Wortfeld: sagen",True
SetFont Arial_55
Text 640,70,"Liste aller Synonyme",True
SetFont Arial_40
Text 640,950,"-Weiter mit beliebiger Taste-",True
SetFont Arial_35


WAKX=125
WAKY=130

Repeat



ZufallO$ = Wort$(Ziffer)
Zufall$=Zufall$+ZufallO$
Ziffer=Ziffer+1
If WAKY>850 And WAKX=125 Then WAKY=130 WAKX=375
If WAKY>850 And WAKX=375 Then WAKY=130 WAKX=625
If WAKY>850 And WAKX=625 Then WAKY=130 WAKX=875
If WAKY>850 And WAKX=875 Then WAKY=130 WAKX=1125
WAKY=WAKY+37
Text WAKX,WAKY,ZufallO$,True
EDVWS=EDVWS+1
Delay 100
Until WAKY>850 And WAKX=1125
FlushKeys
WaitKey()


Aufgabe_abgebrochen=0 : SpielstandSLR
Belohnungsmuenzen=Belohnungsmuenzen+WSR


Cls
HGrundH=LoadImage(".\Bilder\Sonnenuntergang.jpg")
DrawBlock HGrundH, 0,0
SetFont Arial_100
Text 640,10,"Wortfeld: sagen",True
SetFont Arial_50
Text 640,180,"Du hast "+WSR+" richtige Wörter zum Wortfeld: sagen",True
Text 640,230,"in drei Minuten aufgeschrieben!!!",True
If WSR>10 Or WSR=10 Then Text 640,280,"Ziel erreicht!",True
If WSR<10 Then
	Text 640,280,"Ziel nicht erreicht!",True
	Text 640,330,"Versuche die Aufgabe erneut!",True
EndIf
SetFont Arial_40
Text 640,950,"-Weiter mit beliebeger Taste-",True
SpielstandSN("Punkte: "+WSR)
FlushKeys
WaitKey()


If WSR<10 Then
	SpielstandSN("Zu viele Fehler!")

	NAWH_Image=SElba
	Gosub NAWH
	Goto Aufgabe13
EndIf

If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=13
Goto Programmstart
End





.WoerterLWS

;102

Data "flüstern","zischen","murmeln","hauchen","wispern","tuscheln","rufen","schreien","brüllen","kreischen"
Data "krakeelen","reden","sagen","plaudern","sprechen","quasseln","plappern","schnattern","bemerken","andeuten"
Data "meinen","faseln","äussern","schwatzen","erzählen","berichten","erwähnen","erklären","beschliessen","überlegen"
Data "glauben","schildern","erläutern","raten","reimen","dichten","übertreiben","lügen","loben","sich erinnern"
Data "erinnern","von sich geben","geben","prahlen","wiederholen","weinen","schluchzen","plärren","seufzen","stöhnen"
Data "jammern","klagen","trösten","kichern","fragen","sich erkundigen","erkundigen","antworten","erwidern","bejahen"
Data "verneinen","wiedersprechen","entgegnen","behaupten","zustimmen","verkünden","bezeugen","einwenden","einwerffen","unterbrechen"
Data "befehlen","auffordern","vorschlagen","bitten","betteln","versichern","versprechen","schimpfen","sich beschweren","beschweren"
Data "tadeln","reklamieren","sich ärgern","ärgern","schmollen","lästern","drohen","zurechtweisen","anschnauzen","protesteiren"
Data "petzen","zanken","fluchen","stottern","lallen","nuscheln","krächzen","stammeln","brummen","labern"
Data "hervorstossen","näseln"












.Aufgabe14
Aufgabe_Aufgabenname$="Malrechnen"
Goto Aufgabe_1_12_14
End







.Aufgabe15
SpielstandSN("Wörter merken")
Aufgabe_abgebrochen=1 : SpielstandSLR
PauseChannel HGM
WMP#=0
AWM=0
WMAZZWL=0
SeedRnd MilliSecs()
ClsVB
FreeImage HGrundH
HGrundH=LoadImage (".\Bilder\Sonnenuntergang.jpg")
DrawBlock SElba, 0,0
Color 0,0,0
Locate 0,20
SetFont Arial_100
Print " Wörter merken"
SetFont Arial_40
Print ""
Print "	Mit einem Druck auf Enter erscheinen 20 zufällige Wörter."
Print "	Merke dir so viele wie möglich."
Print "	Jedes Wort in der richtigen Reihenfolge ergibt 1 Punkt,"
Print "	Jedes Wort in der falschen Reihenfolge einen halben Punkt"
Print "	und jedes falsche Wort 0 Punkte."
Print "	Um das Ziel zu erreichen, musst du mindestens 10 von 20 Punkten haben."
Print ""
Print "	Tipp:"
Print "	Merke dir die Wörter z.B. mit einer Bildergeschichte! So geht es für"
Print "	mich am einfachsten. Auch da bei dieser Übung die korrekte Reihenfolge"
Print "	eine Rolle spielt. Wenn du nämlich dir die Wörter in der korrekten"
Print "	Reihenfolge merkst, reicht es auch schon nur die ersten 10 zu merken"
Print "	um das Ziel zu erreichen."	
Print ""
Print "	Viel Glück!"
Print ""
Print "	-Aufgabe starten mit beliebiger Taste-"
WaitKey()
StartZeit = MilliSecs()
Restore WoerterWM
 ;Einleseschlaufe für 140 Wörter
      Const MaxWM = 125
Dim Wort$(MaxWM)

Color 255,255,255
            

.WoerterWM

Data "Kerze","Vase","Computer","Batterie","Kaktus","Stein","Kugel","Puzzle","Lautsprecher","Lampe","Sand","Glas","Teller","Kabel","Stecker","Bleistift","Kugelschreiber","Farbstift"
Data "Taschentuch","Lineal","Gummi","Salat","Engel","Hose","Socken","Bett","Poster","Heft","Buch","Schere","CD","Klebeband","Licht","Fenster","Keller","Spiegel"
Data "Schokolade","Leim","Xylophon","Pflanze","Webcam","Lego","Murmel","Stachel","Memorystick","Witz","Uhr","Draht","Harfe","Gitarre","Klavier","Bad","Dusche","Topf"
Data "Strom","Paris","Foto","Heizung","Schere","Geld","Münzen","Holz","Muschel","Telefon","Brille","Film","Kamera","Floss","Arm","Gürtel","Tasche","Kind"
Data "Pizza","Schachtel","Schlauch","Karton","Wäsche","Ton","Zimmer","Haus","Türe","Klingel","Velo","Auto","Kompost","Garten","Rose","Stuhl","Kiste","Auge"
Data "Zahn","Maus","Katze","Hund","Postkarte","Brief","Seil","Knoten","Turm","Pisa","Griff","Abfall","Hand","Taschenrechner","Gehirn","Schule","Löffel","Käfer"
Data "Spinne","Bildschirm","Würfel","Vogel","Eule","Hufeisen","Kopfhörer","Treppe","Wand","See","Meer","Figur","Stab","Name","Planet","Erde","Sofa","Möbel"

For i = 0 To MaxWM
	Read Wort$(i)
Next


DrawBlock HGrundH, 0,0
SetFont Arial_55
Color 0,0,0
WMAZZWL=0
Text 640,10,"Wörter merken",True
SetFont Arial_40

Repeat
	WMAZZWL=WMAZZWL+1
	WMZM$(WMAZZWL)=Wort$(Rand (0,125))
	Text 640,WMAZZWL*40+50,WMZM$(WMAZZWL),True
Until WMAZZWL=20

Text 640,950,"-Weiter mit beliebiger Taste-",True
WaitKey
Cls
Locate 0,20
AWM=0

DrawBlock SElba, 0,0

Repeat
	AWM=AWM+1
	QWM$(AWM)=Input(String("	",17)+AWM+":	")
Until AWM=20

AWM=0
Cls
Locate 0,0
DrawBlock HGrundH, 0,0



SetFont Arial_55
Text 640,10,"Korrigierte Wörter",True
SetFont Arial_30
Text 640,70,"(grün: richtig, orange: richtig aber an falscher Stelle, rot: Falsch)",True
Text 640,970,"-Weiter mit beliebiger Taste-",True
SetFont Arial_40

Repeat
	BSCHTWM=0
	fgaijk=0
	fjkfbuaedkl=0
	rvmbfthjbn=0
	Color 255,0,0
	AWM=AWM+1
	rvmbfthjbn=0
	Repeat
		fgaijk=fgaijk+1
		If QWM(AWM)=NBIMWM$(fgaijk) Then BSCHTWM=1
	Until fgaijk=20
	If QWM$(AWM)=WMZM$(AWM) And BSCHTWM=0 Then
		Color 0,255,0
		WMEVR=2
	Else
		Repeat
			rvmbfthjbn=rvmbfthjbn+1
			If QWM(AWM)=WMZM(rvmbfthjbn) Then fjkfbuaedkl=1
		Until rvmbfthjbn=20

		If fjkfbuaedkl=1 Then
			If BSCHTWM=0 Then Color 255,155,50 WMEVR=1 Else Color 255,0,0 WMEVR=0
		Else
			Color 255,0,0
			WMEVR=0
		EndIf
	EndIf
	
	NBIMWM$(AWM)=QWM(AWM)
	Text 640,AWM*40+90,QWM(AWM),1
	If WMEVR=1 Then WMP#=WMP#+0.5
	If WMEVR=2 Then WMP#=WMP#+1
	Color 255,0,0
Until AWM=20

WaitKey()

Aufgabe_abgebrochen=0
Belohnungsmuenzen=Belohnungsmuenzen+WMP#
SpielstandSLR

Cls
Locate 0,0
DrawBlock HGrundH, 0,0

SetFont Arial_65
Color 0,0,0
Text 640,477,"Du hast "+WMP#+" von 20 Punkten erreicht!",True,True
If WMP#<10 Then Text 640,547,"Ziel nicht erreicht!",True,True
If WMP#>=10 And WMP#<15 Then Text 640,547,"Ziel erreicht!",True,True
If WMP#>=15 And WMP#<18 Then Text 640,547,"Ziel gut erreicht!",True,True
If WMP#>=18 Then Text 640,547,"Ziel sehr gut erreicht!",True,True
SetFont Arial_30
Text 640,970,"Weiter mit beliebiger Taste",True
SpielstandSLR
SpielstandSN("Punkte: "+WMP#+"/20")
Gosub PZeit
WaitKey
If WMP#=>10 And UebersichtA=1 Then Goto Uebersicht3
If WMP#=>10 Then Aufgaben=Aufgaben+1 Goto Programmstart

NAWH_Image=SElba
Gosub NAWH
Goto Aufgabe15
End
















.Aufgabe16

RichtigA16=0
FalschA16=0
Zu_langsamA16=0

FY=0
FX=0
G=1
SFGG=0
ClsVB
G=8
GZSFG

SpielstandSN("Rechenspiel")
Aufgabe_abgebrochen=1 : SpielstandSLR




PauseChannel MXP
Cls
Locate 0,0
ClsVB



Dim Aufgabe16_Steine(9)

Aufgabe16_Steine(0)=LoadImage (".\Bilder\1.bmp")
Aufgabe16_Steine(1)=LoadImage (".\Bilder\2.bmp")
Aufgabe16_Steine(2)=LoadImage (".\Bilder\3.bmp")
Aufgabe16_Steine(3)=LoadImage (".\Bilder\4.bmp")
Aufgabe16_Steine(4)=LoadImage (".\Bilder\5.bmp")
Aufgabe16_Steine(5)=LoadImage (".\Bilder\6.bmp")
Aufgabe16_Steine(6)=LoadImage (".\Bilder\7.bmp")
Aufgabe16_Steine(7)=LoadImage (".\Bilder\8.bmp")
Aufgabe16_Steine(8)=LoadImage (".\Bilder\9.bmp")

Blaetter_TileImage=LoadImage (".\Bilder\Blätter.jpg")

Stars_TileImage=LoadImage (".\Bilder\stars.bmp")

Blaetter = CreateImage (1280,2048)
SetBuffer ImageBuffer(Blaetter)
TileBlock Blaetter_TileImage
SetBuffer FrontBuffer()


Aufgabe16_Steine(9) = CreateImage (50,50)
SetBuffer ImageBuffer(Aufgabe16_Steine(9))

Color 0,0,0
Rect 0,0,50,50,1

SetBuffer FrontBuffer()

Dim SteinK(5,8) ;Reihe, Spalte
Dim ZahlenK(5,8) ;Reihe, Spalte

Dim GetroffenS(6)
Dim Aufgabe_16_Eingabe(6)
Dim Aufgabe_16_Eingabe_str$(6)
Dim Fehler16SS(8)


Dim SteinTempo(5)

Dim SteinPos(5,2)	;(Spalten Nummer, 0=X Position & 1=Y Position)

SteinPos(0,0)=50
SteinPos(1,0)=100
SteinPos(2,0)=150
SteinPos(3,0)=200
SteinPos(4,0)=250
SteinPos(5,0)=350

SteinPos(0,1)=0
SteinPos(1,1)=0
SteinPos(2,1)=150
SteinPos(3,1)=200
SteinPos(4,1)=250
SteinPos(5,1)=350

PauseChannel HGM
ClsVB

SFRXP(1) = CreateImage (18*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXP(1))
DrawImageRect SF(g), 0,0, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
SetBuffer FrontBuffer()
SFRXP(2) = CreateImage (22*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXP(2))
DrawImageRect SF(g), 0,0, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
SetBuffer FrontBuffer()
SFRXP(3) = CreateImage (22*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXP(3))
DrawImageRect SF(g), FX, FY, 41*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
SetBuffer FrontBuffer()
SFRXP(4) = CreateImage (19*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXP(4))
DrawImageRect SF(g), 0,0, 60*SFG#, 32*SFG#, 19*SFG#, 32*SFG#
SetBuffer FrontBuffer()


SFRXM(1) = CreateImage (19*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXM(1))
DrawImageRect SF(g), 0,0, 0*SFG#, 0*SFG#, 19*SFG#, 32*SFG#
SetBuffer FrontBuffer()
SFRXM(2) = CreateImage (22*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXM(2))
DrawImageRect SF(g), FX, FY, 19*SFG#,0*SFG#, 22*SFG#, 32*SFG#
SetBuffer FrontBuffer()
SFRXM(3) = CreateImage (19*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXM(3))
DrawImageRect SF(g), 0,0, 41*SFG#, 0*SFG#, 19*SFG#, 32*SFG#
SetBuffer FrontBuffer()
SFRXM(4) = CreateImage (22*SFG#,32*SFG#)
SetBuffer ImageBuffer (SFRXM(4))
DrawImageRect SF(g), 0,0, 60*SFG#, 0*SFG#, 22*SFG#, 32*SFG#
SetBuffer FrontBuffer()


rocket=SFRXP(1)


TileBlock Aufgabenstellung_Hintergrund

Color 0,0,0
Locate 0,20

SetFont Arial_90
Print " Rechenspiel"
SetFont Arial_40
Print ""
Print "	Nach einem Klick auf eine beliebige Taste,"
Print "	erscheinen viele hinabfallende Zahlen."
Print "	Wenn deine Spielfigur eine der Zahlen"
Print "	berührt, dann wird sie bei der nächsten"
Print "	Ziffer der Rechnung eingetragen."
Print "	das Ziel ist, so eine Rechnumg mit ihrem korrekten"
Print "	Resultat zu erstellen."
Print "	Achtung! Du hast für eine Rechnung nur solange Zeit bis"
Print "	deine Spielfigur am unteren Bildrand ankommt."
Print "	Es sind 7 Aufgaben zu lösen. Jede richtige ergibt"
Print "	je eine Belohnungsmünze. Jede falsche oder zu langsamn"
Print "	gelöste jedoch auch eine Abzug!"
Print ""
Print "	Übrigens: Beim Resultat müssen nicht alle ? ausgefüllt"
Print "	werden. Richtig wäre z.B. auch 2*2=4? oder 22+4=26?."
Print ""
Print "	Noch etwas: Diese Aufgabe musst du nicht wiederholen,"
Print "	egal wie schlecht du bist. Das hat den Grund, dass es"
Print "	Leute gibt die so einfach keine Aufgaben lösen können."
Print "	Auch das freiwillige Wiederholen, empfehle ich nur denen,"
Print "	die auch wirklich mit solchen Aufgaben lernen können."


WaitKey()

StartZeit = MilliSecs()
If Schwierigkeitsstufe=1 Then SchwierigkeitsstufeRST=Schwierigkeitsstufe
If Schwierigkeitsstufe=2 Then SchwierigkeitsstufeRST=Schwierigkeitsstufe : Schwierigkeitsstufe=1
If Schwierigkeitsstufe=3 Then SchwierigkeitsstufeRST=Schwierigkeitsstufe : Schwierigkeitsstufe=2
If Schwierigkeitsstufe=4 Then SchwierigkeitsstufeRST=Schwierigkeitsstufe : Schwierigkeitsstufe=2

StartZeitP = MilliSecs()
hfdujkhbgjgjuvkhuj16=1
hfdujkhbgjgjuvkhuj161=1








Repeat
SFSCHNR=1
SteinKoY=0
SteinKoX=0
SteinAnz=0

For i=0 To 6
	Aufgabe_16_Eingabe(i)=0
	Aufgabe_16_Eingabe_str$(i)="?"
Next

SteinKoY=100
SteinKoX=400
SteinAnz=0
hnjugbjbhujmbg16=9
NulP16=0

PSEE161#=0 ; Sehr wichtig! Sonst gehen mit der Zeit die ersten beiden Kugeln aus dem Bildschirm!

Aufgabe_16_Eingabe_Zaehler=0

For i1=0 To 5
	For i2=0 To 8
		Rand_Temp=Rand(0,8)
		SteinK(i1,i2)=Aufgabe16_Steine(Rand_Temp)
		ZahlenK(i1,i2)=Rand_Temp+1
	Next
Next


SetFont Arial_90
Color 255,255,200


SetBuffer BackBuffer ()
x = 0
y = 169

HGrundH=Bild1


SteinKoY=100
SteinKoX=400
SteinAnz=0





Repeat
hnjugbjbhujmbg16=hnjugbjbhujmbg16-1
If hnjugbjbhujmbg16=0 Then hnjugbjbhujmbg16=10 NulP16=NulP16+1


If NulP16=800 Then
	ClsVB
	Origin 0,0
	TileImage Stars_TileImage, 0,0
	Text 640,512,"Zu langsam",True,True
	Delay 2000
	Zu_langsamA16=Zu_langsamA16+1
	FalschA16=FalschA16+1
	Exit
EndIf


Origin 0,NulP16



DrawBlock Blaetter, 0,-1024
Rect 40,90,1200,130,True

If Schwierigkeitsstufe=2 Then Text 50,1,Aufgabe_16_Eingabe_str$(0)+Aufgabe_16_Eingabe_str$(1)+"+"+Aufgabe_16_Eingabe_str$(2)+"="+Aufgabe_16_Eingabe_str$(3)+Aufgabe_16_Eingabe_str$(4)+Aufgabe_16_Eingabe_str$(5)
If Schwierigkeitsstufe=1 Then Text 50,1,Aufgabe_16_Eingabe_str$(0)+"*"+Aufgabe_16_Eingabe_str$(1)+"="+Aufgabe_16_Eingabe_str$(2)+Aufgabe_16_Eingabe_str$(3)

Text 900,1,"Aufg. "+(RichtigA16+FalschA16)+"/7"


If Aufgabe_16_Eingabe(0)>0 And Schwierigkeitsstufe=2 And Aufgabe_16_Eingabe(0)*10+Aufgabe_16_Eingabe(1)+Aufgabe_16_Eingabe(2)=Aufgabe_16_Eingabe(3)*100+Aufgabe_16_Eingabe(4)*10+Aufgabe_16_Eingabe(5) Then
ClsVB
Color 0,255,0
Origin 0,0
TileImage Stars_TileImage, 0,0
Text 640,512,"Richtig!",True,True
Delay 2000
RichtigA16=RichtigA16+1
Exit


ElseIf Aufgabe_16_Eingabe(0)>0 And Schwierigkeitsstufe=2 And Aufgabe_16_Eingabe(0)*10+Aufgabe_16_Eingabe(1)+Aufgabe_16_Eingabe(2)=Aufgabe_16_Eingabe(3)*10+Aufgabe_16_Eingabe(4) Then
ClsVB
Color 0,255,0
Origin 0,0
TileImage Stars_TileImage, 0,0
Text 640,512,"Richtig!",True,True
Delay 2000
RichtigA16=RichtigA16+1
Exit




ElseIf Aufgabe_16_Eingabe(2)=1 Or Aufgabe_16_Eingabe(2)>1 And Schwierigkeitsstufe=1 And Aufgabe_16_Eingabe(0)*Aufgabe_16_Eingabe(1)=Aufgabe_16_Eingabe(2)*10+Aufgabe_16_Eingabe(3) Then
ClsVB
Color 0,255,0
Origin 0,0
TileImage Stars_TileImage, 0,0
Text 640,512,"Richtig!",True,True
Delay 2000
RichtigA16=RichtigA16+1
Exit



ElseIf Aufgabe_16_Eingabe(2)=1 Or Aufgabe_16_Eingabe(2)>1 And Schwierigkeitsstufe=1 And Aufgabe_16_Eingabe(0)*Aufgabe_16_Eingabe(1)=Aufgabe_16_Eingabe(2)+Aufgabe_16_Eingabe(3) Then
ClsVB
Color 0,255,0
Origin 0,0
TileImage Stars_TileImage, 0,0
Text 640,512,"Richtig!",True,True
Delay 2000
RichtigA16=RichtigA16+1
Exit

ElseIf Schwierigkeitsstufe=2 And Aufgabe_16_Eingabe(5)>0 Or Schwierigkeitsstufe=1 And Aufgabe_16_Eingabe(3)>0 Then
ClsVB
Color 255,0,0
Origin 0,0
TileImage Stars_TileImage, 0,0
Text 640,512,"Falsch!",True,True
Delay 2000
FalschA16=FalschA16+1
Exit
EndIf



hgjkhkgkh=0



If x=<40 Then x=40
If x=>1200 Then x=1200



;Letzte Reihe wieder an den Anfang setzen
;Dabei werden eigendlich aber nur alle Reihen eine Reihe hochgesetzt.
;--------------------------------------------------------
SteinKoY=SteinKoY+1
If SteinKoY=1500 Then
	SteinKoY=1050

	For i1=0 To 4
		For i2=0 To 8
			SteinK(i1,i2)=SteinK(i1+1,i2)
			ZahlenK(i1,i2)=ZahlenK(i1+1,i2)
		Next 
	Next
	
	For i=0 To 8
		Rand_Temp=Rand(0,8)
		SteinK(5,i)=Aufgabe16_Steine(Rand_Temp)
		ZahlenK(5,i)=Rand_Temp+1
	Next
	
EndIf
	
;--------------------------------------------------------



SZGVGVGH=0



PSEE161#=PSEE161#+0.1

SteinPos(0,1)=SteinPos(0,1)-PSEE161#
SteinPos(1,1)=SteinPos(1,1)+PSEE161#




;Anzeigen der Hinabfallenden Steine und überprüfung auf kolision
;-----------------------------------------------------------------------------------------------------------
For SteinAnz=0 To 5;14

	For i=0 To 5
		DrawImage SteinK(SteinAnz,i), SteinKoX+SteinPos(i,0),SteinKoY-SteinPos(i,1)
		ICrocket=ImagesCollide (rocket, x,y,0, SteinK(SteinAnz,i),SteinKoX+SteinPos(i,0),SteinKoY-SteinPos(i,1),0)
 		If ICrocket Then
			Aufgabe_16_Eingabe(Aufgabe_16_Eingabe_Zaehler)=ZahlenK(SteinAnz,i)
			Aufgabe_16_Eingabe_str$(Aufgabe_16_Eingabe_Zaehler)=Aufgabe_16_Eingabe(Aufgabe_16_Eingabe_Zaehler)
			Aufgabe_16_Eingabe_Zaehler=Aufgabe_16_Eingabe_Zaehler+1
			SteinK(SteinAnz,i)=Aufgabe16_Steine(9)
		EndIf
	Next
	
SteinKoY=SteinKoY-450

Next
;-----------------------------------------------------------------------------------------------------------



SteinPos(0,1)=SteinPos(0,1)+PSEE161#
steinPos(1,1)=SteinPos(1,1)-PSEE161#



SteinKoY=SteinKoY+2700;2700;6750


JetztZeit = MilliSecs()
If (JetztZeit-StartZeit > ZeitMaxRS) Then
If SFSCHNR=4 Then SFSCHNR=0
SFSCHNR=SFSCHNR+1
    If KeyDown (205) = 1 Then x = x + 12:rocket=SFRXP(SFSCHNR)
    If KeyDown (203) = 1 Then x = x - 12:rocket=SFRXM(SFSCHNR)
StartZeit = MilliSecs()
EndIf
    DrawImage rocket, x, y
    Flip
Cls
Forever


Until RichtigA16+FalschA16=7 ;Anzahl Aufgaben


Schwierigkeitsstufe=SchwierigkeitsstufeRST

StartZeit=StartZeitP


Aufgabe_abgebrochen=0

Belohnungsmuenzen=Belohnungsmuenzen+(RichtigA16-FalschA16)
SpielstandSLR



SpielstandSN(RichtigA16+"/7 Punkten")
SpielstandSN("Richtig: "+RichtigA16)
SpielstandSN("Falsch: "+(FalschA16-Zu_langsamA16))
SpielstandSN("Zu langsam: "+Zu_langsamA16)
Gosub PZeit



ClsVB

SetFont Arial_50
Color 255,225,0
Origin 0,0
TileImage Stars_TileImage, 0,0


Text 640,950,"-Weiter mit beliebiger Taste-",True

Text 640,50,"Du hast "+RichtigA16+"/7 Punkten erreicht!",True

If RichtigA16=7 Then		;7
	Text 640,100,"Wow, du warst spitze!"
	Text 640,150,"Du hast alle Aufgaben korrekt gelöst!",True
ElseIf RichtigA16>4 Then	;5,6
	Text 640,100,"Du warst spitze!",True
	Text 640,150,"Du hast fast alle Aufgaben korrekt gelöst!",True
ElseIf RichtigA16=4 Then	;4
	Text 640,100,"Du warst OK",True
	Text 640,150,"Kannst dich aber gut noch verbessern!",True
ElseIf RichtigA16>1 Then	;2,3
	Text 640,100,"Du warst nicht so gut!",True
	Text 640,150,"Wenigstens hast aber du "+RichtigA16+" Rechnungen richtig gelöst!",True
ElseIf RichtigA16=1 Then ;1
	Text 640,100,"Du warst katastrophal!",True
	Text 640,150,"Du hast nur 1 Rechnung richtig gelöst!",True
Else						;0
	Text 640,100,"Du warst katastrophal!",True
	Text 640,150,"Du hast keine einzige Rechnung richtig gelöst!",True
EndIf


If RichtigA16>3 Then		;4,5,6,7
	Text 640,250,"Ich vermute, dass dir diese Aufgabenart gut liegt und es",True
	Text 640,300,"bei dir Sinn macht diese Aufgabe, wenn du Mal Zeit hast,",True
	Text 640,350,"noch einige Male zu wiederholen.",True
	Text 640,400,"Sind wir jetzt mal ehrlich: Eigentlich macht diese Aufgabe",True
	Text 640,450,"ja wirklich Spass.",True
	Text 640,500,"Und genau so soll es sein! Mein ganzes Lernprogramm! :D",True
	Text 640,550,"Eigentlich sollte alles Spass machen! :D :D :D :D :D :D :D",True
Else						;0,1,2,3
	Text 640,250,"Ich vermute, dass du nicht den Typ für solche Aufgaben bist",True
	Text 640,300,"und empfehle dir deswegen diese Aufgabe nicht mehr zu",True
	Text 640,350,"wiederholen. Sie ist für dich mit grösster Wahrscheinlichkeit",True
	Text 640,400,"nur eine Zeitverschwendung. Nütze die Zeit lieber mit",True
	Text 640,450,"anderen Aufgaben, die dir besser liegen.",True
EndIf


WaitKey()


If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=16
Goto Programmstart
End




















.Aufgabe17

SpielstandSN("Schnellrechnen")
Aufgabe_abgebrochen=1 : SpielstandSLR


PauseChannel MXP
ClsVB

NAWH_Image=SElba


Aufgabehhhjjhujh=0
Richtig=0
Falsch=0
Zahl1=0
Zahl2=0
Zahl3=0
SROP=0


If Schwierigkeitsstufe<3 Then	;1,2
	ZeitMaxSR = 20000  ; 15 Sekunden
Else							;3,4
	ZeitMaxSR = 15000  ; 20 Sekunden
EndIf



TileBlock Aufgabenstellung_Hintergrund
Color 0,0,0
Locate 0,20

SetFont Arial_100
Print " Schnellrechnen"
SetFont Arial_40
Print ""
Print "	Mit einem Druck auf eine beliebige erscheint eine"
Print "	+,-,* oder : Rechnung."
Print "	Du hast "+(ZeitMaxSR/1000)+" Sekunden Zeit um die Rechnung"
Print "	zu lösen."
Print ""
Print "	Du musst von 30 Rechnungen mindestens 20"
Print "	richtig und in angemessenem Tempo schaffen."
Print "	Sonst musst du die Aufgabe wiederholen."
Print ""
Print "	Jede richtige Aufgabe ergibt 1 Belohnungsmünze."
Print "	Jede falsche oder zu langsame dagegen 1 Münze Abzug."
Print ""
Print "	Achtung:"
Print "	Zwischen den Aufgaben gibt es keine Pausen!"
Print ""
Print "	Übrigens:"
Print "	Durch die Textfarbe der Korrektur "+Chr$(34)+"Zu langsam!"+Chr$(34)
Print "	kannst du schliessen, ob du die Rechnung eigentlich"
Print "	abgesehen von der Zeit richtig gelöst hast.
Print "	(gelb=Richtig	orange=Falsch)"

WaitKey()

StartZeitP = MilliSecs()
Cls
Locate 0,20

DrawBlock SElba, 0,0

FreeFont Schrift
Schrift = LoadFont ("Arial", 43,True)	;genau Arial 43 ist wichtig! Sonst geht Design nicht auf!
SetFont Schrift

SeedRnd MilliSecs()


For i1=0 To 2		;3 Seiten
For i2=0 To 9		;mit je 10 Rechnungen

StartZeit = MilliSecs()

Color 0,0,0
SROP=Rand (1,4)







;Plusaufgaben
;-------------------------------------------------------------------------------
If SROP=1 Then
	If Schwierigkeitsstufe=1 Then
		Zahl1= Rand(0,50)
		Zahl2= Rand(0,50)
	Else
		Zahl1= Rand(0,100)
		Zahl2= Rand(0,100)
	EndIf

	Ergebnis=Input("	"+Zahl1+"+"+Zahl2+"=")
	
	JetztZeit = MilliSecs()
	

	
	If (JetztZeit-StartZeit > ZeitMaxSR) Then
		If Ergebnis=Zahl1+Zahl2 Then Color 223,255,0 Else Color 255,127,39
		Print "	Zu spät!"
	Else
		If Ergebnis=Zahl1+Zahl2 Then Color 34,177,76 Else Color 237,28,36
		If Ergebnis=Zahl1+Zahl2 Then Print "	Richtig!" : Richtig=Richtig+1 Else Print "	Falsch!" : Falsch=Falsch+1
	EndIf

EndIf
;-------------------------------------------------------------------------------





;Minusaufgaben
;-------------------------------------------------------------------------------
If SROP=2 Then
	Zahl1= Rand(0,100)
	Zahl2= Rand(0,100)
	If Zahl1<Zahl2 Then Zahl3=Zahl1 Zahl1=Zahl2 Zahl2=Zahl3
	Ergebnis=Input("	"+Zahl1+"-"+Zahl2+"=")
	JetztZeit = MilliSecs()
	
	
	If (JetztZeit-StartZeit > ZeitMaxSR) Then
			If Ergebnis=Zahl1-Zahl2 Then Color 223,255,0 Else Color 255,127,39
		Print "	Zu spät!"
	Else
		If Ergebnis=Zahl1-Zahl2 Then Color 34,177,76 Else Color 237,28,36
		If Ergebnis=Zahl1-Zahl2 Then Print "	Richtig!" : Richtig=Richtig+1 Else Print "	Falsch!" : Falsch=Falsch+1
	EndIf

EndIf
;-------------------------------------------------------------------------------





;Malaufgaben
;-------------------------------------------------------------------------------
If SROP=3 Then
	Zahl1= Rand(1,10)
	Zahl2= Rand(0,10)
	Ergebnis=Input("	"+Zahl1+"*"+Zahl2+"=")
	JetztZeit = MilliSecs()
	
	
	If (JetztZeit-StartZeit > ZeitMaxSR) Then
			If Ergebnis=Zahl1*Zahl2 Then Color 223,255,0 Else Color 255,127,39
		Print "	Zu spät!"
	Else
		If Ergebnis=Zahl1*Zahl2 Then Color 34,177,76 Else Color 237,28,36
		If Ergebnis=Zahl1*Zahl2 Then Print "	Richtig!" : Richtig=Richtig+1 Else Print "	Falsch!" : Falsch=Falsch+1
	EndIf

EndIf
;-------------------------------------------------------------------------------





;Durchaufgaben
;-------------------------------------------------------------------------------
If SROP=4 Then
	Zahl3= Rand(0,10)
	Zahl2= Rand(1,10)
	Zahl1= Zahl2*Zahl3
	Ergebnis=Input("	"+Zahl1+":"+Zahl2+"=")
	JetztZeit = MilliSecs()
	
	
	If (JetztZeit-StartZeit > ZeitMaxSR) Then
			If Ergebnis=Zahl1/Zahl2 Then Color 223,255,0 Else Color 255,127,39
		Print "	Zu spät!"
	Else
		If Ergebnis=Zahl1/Zahl2 Then Color 34,177,76 Else Color 237,28,36
		If Ergebnis=Zahl1/Zahl2 Then Print "	Richtig!" : Richtig=Richtig+1 Else Print "	Falsch!" : Falsch=Falsch+1
	EndIf

EndIf
;-------------------------------------------------------------------------------



Next

Delay 1000
Cls
Locate 0,20
DrawBlock SElba, 0,0

Next




Aufgabe_abgebrochen=0 : SpielstandSLR

StartZeit=StartZeitP
Gosub PZeit

JetztZeit=MilliSecs()


Aufgabe_abgebrochen=0

Belohnungsmuenzen=Belohnungsmuenzen+(Richtig-Falsch)
SpielstandSLR



Aufgabe_17_Durchschnitssekunden=(JetztZeit-StartZeitP)/30000
Aufgabe_17_Durchschnitstausendstelsekunden=(JetztZeit-StartZeitP)/30

SpielstandSN("Richtig: "+Richtig+"/30")
SpielstandSN("Durchschnitt pro Rechnung: "+Aufgabe_17_Durchschnitssekunden+"S "+(Aufgabe_17_Durchschnitstausendstelsekunden-(Aufgabe_17_Durchschnitssekunden*1000))+"ms") ;Durchschnit pro rechnung



Cls
Color 0,0,0
SetFont Arial_50

DrawBlock SElba, 0,0

Text 640,870,"-Weiter mit beliebiger Taste-",True

Text 640,50,"Du hast "+Richtig+"/30 Aufgaben",True
Text 640,110,"richtig und in angemesenem Tempo gelöst.",True

If Richtig=>25 Then
	Text 640,170,"Herzlichen Glückwunsch:",True
	Text 640,230,"Du hast das Ziel erreicht!",True

	WaitKey()

	If UebersichtA=1 Then Goto Uebersicht3
	Aufgaben=17
	Goto Programmstart
Else
	Text 640,170,"Du hast das Ziel nicht erreicht!",True
	Text 640,230,"Versuche die Aufgabe erneut!",True

	WaitKey()

	Gosub NAWH
	Goto Aufgabe17
EndIf
End




















.Aufgabe18
ClsVB
SpielstandSN("Adjektive")
Aufgabe_abgebrochen=1 : SpielstandSLR
NVAorP=3

NAWH_Image=SElba
PauseChannel HGM
StartZeit = MilliSecs()
FlushKeys
Fehler=0

Cls
Locate 0,0

DrawBlock SElba, 0,0
SetFont Arial_20
Print ""
SetFont Arial_100
Print " Adjektive"
SetFont Arial_40
Print ""
Print "	Löse die folgenden Aufgaben."
Print "	Die Aufgabe gibt maximagl 18 Belohnungsmünzen."
Print "	Für jede falsche Aufgabe gibt es zwei Abzug."
Print "	Je nach Schwierigkeitsstufe wird aber nach"
Print "	einer bestimmten Anzahl Fehlern die Aufgabe"
Print "	abgebrochen und du musst von vorne beginnen."
Print "	Viel Spass!"
Print ""
Print "	-Weiter mit beliebiger Taste-"

WaitKey()

DrawBlock SElba, 0,0

SetFont Arial_30
Color 0,0,0
Locate 0,20

Print "	Erkenne das Adjektiv in dem folgenden Satz."
Print "	Schreibe das Adjektiv und drücke Enter"
Print
Print "	Mein Fahrrad fährt nicht mehr so gut."
Ueberpruefewort$ = "gut"
Gosub Grammatik_Ueberpruefung

Print "	Steigere das Adjektiv einmal z.B. (gelber)."
Ueberpruefewort$ = "besser"
Gosub Grammatik_Ueberpruefung

Print "	Und jetzt steigere das Adjektiv zweimal z.B. (am gelbsten).
Ueberpruefewort$ = "am besten"
Gosub Grammatik_Ueberpruefung
Delay 1500




Locate 0,20
DrawBlock SElba, 0,0
Fehler = 0
Print "	Erkenne das Adjektiv in dem folgenden Satz."
Print "	Schreibe das Adjektiv und drücke Enter"
Print
Print "	Ich bin vergnügt."
Ueberpruefewort$ = "vergnügt"
Gosub Grammatik_Ueberpruefung

Print "	Steigere das Adjektiv einmal z.B. (gelber)."
Ueberpruefewort$ = "vergnügter"
Gosub Grammatik_Ueberpruefung

Print "	Und jetzt steigere das Adjektiv zweimal z.B. (am gelbsten).
Ueberpruefewort$ = "am vergnügtesten"
Gosub Grammatik_Ueberpruefung
Delay 1500




Locate 0,20
DrawBlock SElba, 0,0
Fehler = 0
Print "	Erkenne das Adjektiv in dem folgenden Satz."
Print "	Schreibe das Adjektiv und drücke Enter"
Print
Print "Dieser Spielplatz ist echt toll."
Ueberpruefewort$ = "toll"
Gosub Grammatik_Ueberpruefung

Print "	Steigere das Adjektiv einmal z.B. (gelber)."
Ueberpruefewort$ = "toller"
Gosub Grammatik_Ueberpruefung

Print "	Und jetzt steigere das Adjektiv zweimal z.B. (am gelbsten).
Ueberpruefewort$ = "am tollsten"
Gosub Grammatik_Ueberpruefung
Delay 1500




Locate 0,20
DrawBlock SElba, 0,0
Fehler = 0
Print "	Erkenne das Adjektiv in dem folgenden Satz."
Print "	Schreibe das Adjektiv und drücke Enter"
Print "	gesucht ist das nicht angeglichene Adijektig z.B. schön
Print "	im Satz Dieses schöne Haus gehört mir!"
Print
Print "	Ich esse gerne Schokolade."
Ueberpruefewort$ = "gern"
Gosub Grammatik_Ueberpruefung

Print "	Steigere das Adjektiv einmal z.B. (gelber)."
Ueberpruefewort$ = "lieber"
Gosub Grammatik_Ueberpruefung

Print "	Und jetzt steigere das Adjektiv zweimal z.B. (am gelbsten).
Ueberpruefewort$ = "am liebsten"
Gosub Grammatik_Ueberpruefung
Delay 1500



Cls 
Locate 0,20
DrawImage SElba, 0,0
Fehler = 0
Print "	Erkenne das Adjektiv in dem folgenden Satz."
Print "	Schreibe das Adjektiv und drücke Enter"
Print "	gesucht ist das nicht angeglichene Adijektig z.B. schön
Print "	im Satz Dieses schöne Haus gehört mir!"
Print
Print "	Heute haben wir viele Fische gefangen."
Ueberpruefewort$ = "viel"
Gosub Grammatik_Ueberpruefung

Print "	Steigere das Adjektiv einmal z.B. (gelber)."
Ueberpruefewort$ = "mehr"
Gosub Grammatik_Ueberpruefung

Print "	Und jetzt steigere das Adjektiv zweimal z.B. (am gelbsten).
Ueberpruefewort$ = "am meisten"
Gosub Grammatik_Ueberpruefung
Delay 1500



Locate 0,20
DrawBlock SElba, 0,0
Fehler = 0
Print "	Erkenne das Adjektiv in dem folgenden Satz."
Print "	Schreibe das Adjektiv und drücke Enter"
Print
Print "	Denkst du, dass die nächste Bushaltestelle nahe ist?."
Ueberpruefewort$ = "nahe"
Gosub Grammatik_Ueberpruefung

Print "	Steigere das Adjektiv einmal z.B. (gelber)."
Ueberpruefewort$ = "näher"
Gosub Grammatik_Ueberpruefung

Print "	Und jetzt steigere das Adjektiv zweimal z.B. (am gelbsten).
Ueberpruefewort$ = "am nächsten"
Gosub Grammatik_Ueberpruefung
Delay 1500




Belohnungsmuenzen=Belohnungsmuenzen+18 : SpielstandSLR

Aufgabe_abgebrochen=0 : SpielstandSLR
Gosub PZeit
If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=18
Goto Programmstart
End







.Aufgabe19
ClsVB
SpielstandSN("Pronomen")
Aufgabe_abgebrochen=1 : SpielstandSLR
NVAorP=4

Regenbogen=LoadImage (".\Bilder\Regenbogen.jpg")

PauseChannel HGM
StartZeit = MilliSecs()
FlushKeys
Fehler=0

Cls
Locate 0,0

DrawBlock SElba, 0,0
SetFont Arial_20
Print ""
SetFont Arial_100
Print " Pronomen"
SetFont Arial_40
Print ""
Print "	Löse die folgenden Aufgaben."
Print "	Die Aufgabe gibt maximagl 12 Belohnungsmünzen."
Print "	Für jede falsche Aufgabe gibt es zwei Abzug."
Print "	Je nach Schwierigkeitsstufe wird aber nach"
Print "	einer bestimmten Anzahl Fehlern die Aufgabe"
Print "	abgebrochen und du musst von vorne beginnen."
Print "	Viel Spass!"
Print ""
Print "	-Weiter mit beliebiger Taste-"
Delay 100
ResizeImage Regenbogen, 1280, 1024
NAWH_Image=Regenbogen
WaitKey()

DrawBlock Regenbogen, 0,0

SetFont Arial_30
Color 0,0,0
Locate 0,20

Print "	Erkenne das Pronomen in dem folgenden Satz."
Print "	Schreibe das Pronomen und drücke Enter"
Print
Print "	Mein Fahrrad fährt nicht mehr so gut."
Ueberpruefewort$ = "mein"
Gosub Grammatik_Ueberpruefung

Print "	Ersetze das Pronomen durch den passenden Artikel."
Ueberpruefewort$ = "das"
Gosub Grammatik_Ueberpruefung
Delay 1500




Locate 0,20
DrawBlock Regenbogen, 0,0
Fehler = 0
Print "	Erkenne das Pronomen in dem folgenden Satz."
Print "	Schreibe das Pronomen und drücke Enter"
Print
Print "	Erich hat eine Grippe."
Ueberpruefewort$ = "eine"
Gosub Grammatik_Ueberpruefung

Print "	Ersetze das Pronomen durch den passenden Artikel."
Ueberpruefewort$ = "die"
Gosub Grammatik_Ueberpruefung
Delay 1500




Locate 0,20
DrawBlock Regenbogen, 0,0
Fehler = 0
Print "	Erkenne das Pronomen in dem folgenden Satz."
Print "	Schreibe das Pronomen und drücke Enter"
Print
Print "	Peter geniesst seine Ferien."
Ueberpruefewort$ = "seine"
Gosub Grammatik_Ueberpruefung

Print "	Ersetze das Pronomen durch den passenden Artikel."
Ueberpruefewort$ = "die"
Gosub Grammatik_Ueberpruefung
Delay 1500



Locate 0,20
DrawBlock Regenbogen, 0,0
Fehler = 0
Print "	Erkenne das Pronomen in dem folgenden Satz."
Print "	Schreibe das Pronomen und drücke Enter"
Print
Print "	Alex schaut zehn Filme hintereinander."
Ueberpruefewort$ = "zehn"
Gosub Grammatik_Ueberpruefung

Print "	Ersetze das Pronomen durch den passenden Artikel."	;2 Belohnungsmünzen
Ueberpruefewort$ = "die"
Gosub Grammatik_Ueberpruefung
Delay 1500




Locate 0,20
DrawBlock Regenbogen, 0,0
Fehler = 0
Print "	Erkenne das Pronomen in dem folgenden Satz."
Print "	Schreibe das Pronomen und drücke Enter"
Print
Print "	Wier fuhren durch einen langen Tunnel"
Ueberpruefewort$ = "einen"
Gosub Grammatik_Ueberpruefung

Print "	Ersetze das Pronomen durch den passenden Artikel."	;2 Belohnungsmünzen
Ueberpruefewort$ = "der"
Gosub Grammatik_Ueberpruefung
Delay 1500





Belohnungsmuenzen=Belohnungsmuenzen+12 : SpielstandSLR

Aufgabe_abgebrochen=0 : SpielstandSLR
Gosub PZeit
If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=19
Goto Programmstart
End







.Grammatik_Ueberpruefung
Repeat
	Eingabewort$ = Input("	")
	
	Eingabewort$=Replace$(Eingabewort$,"ae","ä")
	Eingabewort$=Replace$(Eingabewort$,"oe","ö")
	Eingabewort$=Replace$(Eingabewort$,"ue","ü")


	If Eingabewort$ = Ueberpruefewort$ Then
		If NVAorP=2 Then Color 13,255,13 Else Color 34,177,76 

		Print "	Richtig!"
		Color 0,0,0
		Exit
	Else
		Color 195,20,0;
		;Color 237,28,36		;rot
		;Color 255,127,39	:orange
		Fehler = Fehler+1
		Belohnungsmuenzen=Belohnungsmuenzen-2
		PlayMusic (".\Sounds\Door1.mp3")
		
		If Schwierigkeitsstufe<=2 And Fehler=3 Then
			Goto Grammatik_Ueberpruefung_zu_viele_Fehler
		ElseIf Schwierigkeitsstufe>=3 And Fehler=2 Then
			Goto Grammatik_Ueberpruefung_zu_viele_Fehler
		Else
			Print "	War wohl nix, bitte nochmal versuchen!"
			Color 0,0,0
		EndIf
	EndIf
	;If Schwierigkeitsstufe<=2 And Fehler=3 Then Goto NVAP
	;If Schwierigkeitsstufe>=3 And Fehler=2 Then Goto NVAP
Forever
Print
Return


.Grammatik_Ueberpruefung_zu_viele_Fehler
Print "	Falsch:"
Print "	Du hast zu viele Fehler!"
Print "	Die richtige Lösung wäre "+Chr$(34)+Ueberpruefewort$+Chr$(34)+" gewesen!
Print "	Versuche die Aufgabe später noch einmal!"
Print ""
Print "	-Weiter mit beliebiger Taste-"
WaitKey()
SpielstandSLR
SpielstandSN("Zu viele Fehler")
Gosub NAWH
If NVAorP=1 Then Goto Aufgabe4
If NVAorP=2 Then Goto Aufgabe7
If NVAorP=3 Then Goto Aufgabe18
If NVAorP=4 Then Goto Aufgabe19
Goto Auswahl
End









.LAufgabe
If Not Aufgaben=100 Or Aufgaben=19 Then
SetFont Arial_60
ClsVB
ClsColor 1,255,1
Cls
Text 640,10,"Du kannst die letzte Aufabe erst machen,",True
Text 640,70,"wenn deine Spielfigur wieder in ihrem Haus ist!",True

SetFont Arial_30
Text 640,970,"Weiter mit beliebiger Taste",True
WaitKey
Goto Uebersicht3
EndIf
ClsVB
LAufgabeVer=1
SpielstandSN(Protokoll$="Letzte Aufgabe")
Aufgabe_abgebrochen=1 : SpielstandSLR
ClsColor 1,255,1
Cls
Locate 0,15
Color 0,0,0

SetFont Arial_100
Print " Letzte Aufgabe!"
SetFont Arial_40
Print ""
Print "	Dein Feind, der dich am Anfang vom"
Print "	Spiel entführt hat, will dich fangen!"
Print "	Leider hat er dich auch noch in ein kleines Smiley"
Print "	verwandelt, damit du, wenn er dich fängt, keine"
Print "	Chance gegen ihn hast!"
Print ""
Print "	Noch ein paar Tipps:"
Print "	Der Feind wird immer schneller."
Print "	Auf dem blauen Feld sind beide schneller und auf dem"
Print "	grünen beide langsamer."
Print "	Rote Felder sind, wie Mauern, unpassierbar!"
Print ""
Print "	Wenn du es nicht im ersten Versuch schaffst, ist es"
Print "	auch nicht schlimm, denn nach jedem Fehlversuch kommst"
Print "	du wieder zu diesem Beschrieb."
Print "	Du kanst übrigens bei dieser Aufgabe auch keine"
Print "	Belohnungsmünzen verlieren oder gewinnen!
Print ""
Print "	Viel Glück!"
Print ""
Print "	-Weiter mit beliebiger Taste-"

WaitKey()

ClsVB
SetBuffer BackBuffer ()
rocket = LoadImage (".\Bilder\FEnd.bmp")
MaskImage rocket, 255,255,255
x = 1000
y = 380
HGrundH= LoadImage (".\Bilder\Letzte Aufgabe.jpg")
monster= LoadImage (".\Bilder\Monster1.bmp")
MaskImage monster, 255,255,255
x1=500
y1=500

x2#=800
y2#=800


StartZeit = MilliSecs()
Const ZeitMaxX = 60000  ; 60 Sekunden

F2GE#=1.5 ;Startgeschwindikeit des Feindes

yS#=400
xS#=435
TGESCHWINDIKEIT=4
HINTERNISVZ=2000

FreeFont Schrift
Schrift = LoadFont ("Arial",130,True)
SetFont Schrift





.LA1
JetztZeit = MilliSecs()
DrawImage HGrundH, 1,1
RWZUHRFFCFG=JetztZeit/1000-StartZeit/1000
Text 580,1,60-RWZUHRFFCFG
If KeyDown (205) = 1 Then x1 = x1 + TGESCHWINDIKEIT Richt1$="L" ElseIf KeyDown (203) = 1 Then x1 = x1 - TGESCHWINDIKEIT Richt1$="R" ElseIf KeyDown (208) = 1Then y1 = y1 + TGESCHWINDIKEIT Richt1$="O" ElseIf KeyDown (200) = 1Then y1 = y1 - TGESCHWINDIKEIT Richt1$="U"


DrawImage rocket, x1,y1
DrawImage monster, x2#,y2#


If QENTG$="" Then

If x1=x2# Then x1=x1-1
If y1=y2# Then y1=y1-1

If x2#-x1=>y1-y2# And y1-y2#>0 Then x2#=x2#-F2GE# Richt2$="L" SGe=1
If x1-x2#=>y1-y2# And y1-y2#>0 Then x2#=x2#+F2GE# Richt2$="R" SGe=1
If x2#-x1=>y2#-y1 And y2#-y1>0 Then x2#=x2#-F2GE# Richt2$="L" SGe=1
If x1-x2#=>y2#-y1 And y2#-y1>0 Then x2#=x2#+F2GE# Richt2$="R" SGe=1


If SGe=0 And y2#-y1>x1-x2# And x1-x2#>0 Then y2#=y2#-F2GE# Richt2$="O"
If SGe=0 And y1-y2#>x1-x2# And x1-x2#>0 Then y2#=y2#+F2GE# Richt2$="U"
If SGe=0 And y2#-y1>x2#-x1 And x2#-x1>0 Then y2#=y2#-F2GE# Richt2$="O"
If SGe=0 And y1-y2#>x2#-x1 And x2#-x1>0 Then y2#=y2#+F2GE# Richt2$="U"
SGe=0
EndIf

ICrocket=ImagesCollide (rocket, x1,y1,0, monster,x2,y2,0)
If ICrocket Then
LAufgabeVer=LAufgabeVer+1
Goto LAufgabe
End
  EndIf

rgb=ReadPixel(x1-1,y1-1)
r1=(rgb And $FF0000)/$10000
g1=(rgb And $FF00)/$100
b1=rgb And $FF
rgb=ReadPixel(x1+33,y1-1)
r3=(rgb And $FF0000)/$10000
g3=(rgb And $FF00)/$100
b3=rgb And $FF
rgb=ReadPixel(x1+33,y1+31)
r2=(rgb And $FF0000)/$10000
g2=(rgb And $FF00)/$100
b2=rgb And $FF
rgb=ReadPixel(x1-1,y1+31)
r4=(rgb And $FF0000)/$10000
g4=(rgb And $FF00)/$100
b4=rgb And $FF


If r1>200 Or r2>200 Or r3>200 Or r4>200 Then
If Richt1$="R" Then x1=x1+TGESCHWINDIKEIT
If Richt1$="L" Then x1=x1-TGESCHWINDIKEIT
If Richt1$="U" Then y1=y1+TGESCHWINDIKEIT
If Richt1$="O" Then y1=y1-TGESCHWINDIKEIT
EndIf

If b1>200 Or b2>200 Then
TGESCHWINDIKEIT=6
ElseIf g1>200 Or g2>200 Or g3>200 Or g4>200 Then
TGESCHWINDIKEIT=2
Else
TGESCHWINDIKEIT=4
EndIf


rgb=ReadPixel(x2#-1,y2#-1)
r1=(rgb And $FF0000)/$10000
g1=(rgb And $FF00)/$100
b1=rgb And $FF
rgb=ReadPixel(x2#+49,y2#-1)
r2=(rgb And $FF0000)/$10000
g2=(rgb And $FF00)/$100
b2=rgb And $FF
rgb=ReadPixel(x2#+49,y2#+34)
r3=(rgb And $FF0000)/$10000
g3=(rgb And $FF00)/$100
b3=rgb And $FF
rgb=ReadPixel(x2#-1,y2#+34)
r4=(rgb And $FF0000)/$10000
g4=(rgb And $FF00)/$100
b4=rgb And $FF


	If QENTG$="O" Then y2#=y2#-F2GE#
	If QENTG$="U" Then y2#=y2#+F2GE#
	If QENTG$="R" Then x2#=x2#+F2GE#
	If QENTG$="L" Then x2#=x2#-F2GE#

	
If r1<200 And r2<200 And r3<200 And r4<200
QENTG$=""
Else
If QENTG$="" Then
	If Richt2$="O" Or Richt2$="U" Then
	If x1>x2# Then QENTG$="R" Else QENTG$="L"
	ElseIf Richt2$="L" Or Richt2$="R" Then
	If y1>y2# Then QENTG$="U" Else QENTG$="O"
	EndIf
EndIf
EndIf

If b1>200 Or b2>200 Or b3>200 Or b4>200 And F2GEVERS=0 Then F2GE#=F2GE#*2 F2GEVERS=1
If g1>200 Or g2>200 Or g3>200 Or g4>200 And F2GEVERL=0 Then F2GE#=F2GE#/2 F2GEVERL=1

If F2GEVERS=1 And b1=<200 And b2=<200 And b3=<200 And b4=<200 Then
F2GE#=F2GE#/2
F2GEVERS=0
EndIf

If F2GEVERL=1 And g1=<200 And g2=<200 And g3=<200 And g4=<200 Then
F2GE#=F2GE#*2
F2GEVERL=0
EndIf

Flip
Cls
JetztZeit = MilliSecs()
If (JetztZeit-StartZeit > ZeitMaxX) Then
	Aufgabe_abgebrochen=1 : SpielstandSLR
	SpielstandSN("Versuche: "+LAufgabeVer)
	Goto Ende
EndIf
F2GE#=F2GE#+0.0003
Goto LA1
End





.Ende

ClsVB
FY=700
FX=570
G=1
SFGG=0
GZSFG
HGrundSF=LoadImage (".\Bilder\14.jpg")
SYP


ClsVB
DrawBlock HGrundSF,0,0
SeedRnd MilliSecs()

img=LoadImage(".\Bilder\14.jpg")


Dim matrix(xdiv,ydiv)
For ii = 1 To frames
	For I = 1 To choice
		Repeat
			x=Rnd(0,xdiv)
			y=Rnd(0,ydiv)
		Until matrix(x,y)=0
		matrix(x,y)=ii
	Next
Next
dly=CreateTimer(fps)
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				DrawImageRect img,x*xsize,y*ysize,x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
Delay 2000
Color 0,0,0
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				Rect x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
EZWTSAW=0
AAAER=0
ETNFMB=0


ClsVB

SetFont Arial_100

;FreeFont Schrift
;Schrift = LoadFont ("Arial",50,True)
;SchriftSW = LoadFont ("Arial",100,True)
Color 0,0,0
backdrop=LoadImage(".\Bilder\Schlusswort.jpg")

TileBlock backdrop
Text 640,455,"Lade Bilder...",True

Dim Smiley(98) ;99 Smileys
Dim Welten(19) ;5 Höhle + 1 Brücke + 14 Welten = 20 Bilder


For i=0 To 98
Smiley(i)=LoadImage(".\Bilder\Smileys\Smiley_"+Trim_Zahl$(i+1,3)+".png")
Next


Welten(0)=LoadImage(".\Bilder\Höle1.jpg")
Welten(1)=LoadImage(".\Bilder\Höle2&4.jpg")
Welten(2)=LoadImage(".\Bilder\Höle3.jpg")
Welten(3)=LoadImage(".\Bilder\Höle5.jpg")
Welten(4)=LoadImage(".\Bilder\Höle6.jpg")

Welten(5)=LoadImage(".\Bilder\Brücke.jpg")

For i=6 To 19
Welten(i)=LoadImage(".\Bilder\"+Trim_Zahl$(i-5,2)+".jpg")
Next

TileBlock backdrop
Text 640,455,"Skaliere Bilder...",True

For i=0 To 19
	ScaleImage Welten(i), 0.5,0.5
Next

ClsVB




Schlusswort_Bild = CreateImage (1280,16024)
SetBuffer ImageBuffer (Schlusswort_Bild)

TileBlock backdrop

For i2=0 To 48
	DrawImage Smiley(i2), 50,i2*200
	DrawImage Smiley(i2+49), 1050,i2*200
Next
	
For i2=0 To 23
	DrawImage Smiley(i2+49), 50,9800+(i2*200)
	DrawImage Smiley(i2), 1050, 9800+(i2*200)
Next
	
	
For i=0 To 19
	DrawBlock Welten(i), 320,2895+(i*600) ;2895=Zwei Textzeilen Abstand vom Text!
Next


SetFont Arial_100
Text 640,100,"Schlusswort",True
SetFont Arial_50
Text 640,230,"Nico (98%):",True
Text 640,295,"programmiert",True
Text 640,360,"designt",True
Text 640,425,"dokumentiert",True
Text 640,490,"fotografiert",True
Text 640,555,"Fotos bearbeitet",True
Text 640,620,"geplant",True
Text 640,685,"Installer",True
Text 640,750,"Programmierfehler gesucht",True
Text 640,815,"Programmierfehler verbessert",True
Text 640,880,"Programmidee",True
Text 640,945,"",True
Text 640,1010,"Daniel (1%):",True
Text 640,1075,"fotografiert",True
Text 640,1140,"geplant",True
Text 640,1205,"",True
Text 640,1275,"Christina (0.75%):",True
Text 640,1335,"Programmierfehler gesucht",True
Text 640,1400,"Rechtschreibefehler gesucht",True
Text 640,1465,"Rechtschreibefehler verbessert",True
Text 640,1530,"",True
Text 640,1595,"Aurelia (0.25%)",True
Text 640,1660,"Programmierfehler gesucht",True
Text 640,1725,"",True
Text 640,1790,"Musik (Harfe):",True
Text 640,1855,"Herr Raphael Bussinger, mein Harfelehrer",True
Text 640,1920,"",True
Text 640,1985,"",True
Text 640,2050,"Programmiert mit Blitzbasic 2D",True
Text 640,2115,""
Text 640,2180,"Homepage: http://www.nicobosshard.ch/",True
Text 640,2245,"E-Mail: nico@bosshome.ch",True
Text 640,2310,"",True
Text 640,2375,"Ich hoffe, dass dir mein Programm gefallen hat.",True
Text 640,2440,"",True
Text 640,2505,"",True
Text 640,2570,"Hier noch einige Erinnerungen:",True
Text 640,2635,"",True
Text 640,2700,"Wenn du sie nicht anschauen willst, kannst",True
Text 640,2765,"du den Abspann mit Esc Taste beenden!:",True

SetBuffer FrontBuffer()

DrawBlock Schlusswort_Bild, 0,0
Delay 2000

SetBuffer BackBuffer()



;Abspielen des Abspanns
;---------------------------------------
For i1=0 To -2500 Step -1
	DrawBlock Schlusswort_Bild, 0,i1
	If KeyHit(1)=True Then Exit
	Delay 10
	Flip
Next


If i1=-2501 Then
	For i1=-2502 To -15000 Step -2
		DrawBlock Schlusswort_Bild, 0,i1
		If KeyHit(1)=True Then Exit
		Delay 10
		Flip
	Next
EndIf
;---------------------------------------



ClsVB
FreeImage HGrundH
HGrundH=LoadImage (".\Bilder\Sonnenuntergang.jpg")
DrawBlock HGrundH, 0,0
FreeFont Schrift
Schrift = LoadFont ("Arial",200,True)
SetFont Schrift
Text 640,430,"Ende",True,True
SetFont Arial_80
Text 640,570,"Oder doch nicht ganz?",True,True
Delay 3000


DrawBlock HGrundH, 0,0
SetFont Arial_50
Text 640,25,"Herzlichen Glückwunsch:",True
Text 640,75,"Du bist am Ende meines Lernprogramms angelangt!",True
Text 640,175,"Die Handlungen des Spiels sind zwar vorbei aber",True
Text 640,225,"Du kannst alle Aufgabe noch in der Übersicht",True
Text 640,275,"lösen. Auch ist es möglich deinen Spielstand",True
Text 640,325,"unter den Spielstandoptionen zu löschen und",True
Text 640,375,"nochmals das ganze Abenteuer (am besten in",True
Text 640,425,"(einer anderen Schwierigkeitsstufe nochmals",True
Text 640,475,"von vorne durchzuspielen!",True

Text 640,550,"Übrigens:",True
Text 640,600,"Aufgaben, die von der Übersicht aus gelöst werden",True
Text 640,650,"ergeben genau die gleiche Anzahl Belohnungsmünzen,",True
Text 640,700,"wie diejenigen im Spiel",True

Text 640,800,"Weiterhin viel Spass wünscht dir der Programmierer", True
Text 640,850,"Nico Bosshard!",True


Text 640,950,"-Weiter mit beliebiger Taste-",True

WaitKey()

If UebersichtA=1 Then Goto Uebersicht3
Aufgaben=100
SpielstandSLR
Goto Programmstart
End













.Vorfilm
vfspt=18000
vfsph=22000
ChannelPitch HGM, vfspt
ClsVB
FY=900
FX=70
SFG#=3
Feind=LoadImage (".\Bilder\Monster1.bmp")
HGrundSF=LoadImage (".\Bilder\14.jpg")
HGrundSFBN=LoadImage (".\Bilder\Höle1.jpg")
g=1
VFSWA=22000
Repeat
SXP
SXP
VVSFLZ=VVSFLZ+1
Until VVSFLZ=5
SYP
SYP
SYP
Bild = CreateImage (1280,1024)
SetBuffer ImageBuffer (Bild)
CopyRect 0,0,1280,1024,0,0,FrontBuffer(),ImageBuffer(Bild)
SetBuffer FrontBuffer()
ClsVB

VFFBSF=0
MaskImage Feind,255,255,255

Repeat
VFFBSF=VFFBSF+1
DrawBlock Bild, 1,1
DrawImage Feind, 560,VFFBSF
Delay 3
Flip
Cls
Until VFFBSF=730
VVSFLZ=0
Repeat
DrawBlock HGrundSF, 1,1
DrawImage Feind, 560,VFFBSF
DrawImageRect SF(g),550,VFFBSF+20, 0*SFG#, 64*SFG#, 22*SFG#, 32*SFG#
Delay 3
Flip
Cls
VFMWIEBA=VFMWIEBA+1
VFFBSF=VFFBSF-1
Until VFFBSF=-150
VFMWIEBA=0
SeedRnd MilliSecs()
ClsVB
img = CreateImage (1280,1024)
SetBuffer ImageBuffer (img)
DrawBlock HGrundSF,0,0
VWait
SetBuffer FrontBuffer()
DrawBlock HGrundSF,0,0
Dim matrix(xdiv,ydiv)
For ii = 1 To frames
	For I = 1 To choice
		Repeat
			x=Rnd(0,xdiv)
			y=Rnd(0,ydiv)
		Until matrix(x,y)=0
		matrix(x,y)=ii
	Next
Next
dly=CreateTimer(fps)
Color 0,0,0
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				Rect x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
SeedRnd MilliSecs()
HGML#=1
ClsVB
Delay 1000
img = CreateImage (1280,1024)
SetBuffer ImageBuffer (img)

DrawBlock HGrundSFBN,0,0
SetBuffer FrontBuffer()
Dim matrix(xdiv,ydiv)
For ii = 1 To frames
	For I = 1 To choice
		Repeat
			x=Rnd(0,xdiv)
			y=Rnd(0,ydiv)
			Until matrix(x,y)=0
		matrix(x,y)=ii
	Next
Next
dly=CreateTimer(fps)
For frm=0 To frames
	WaitTimer(dly)
	For x=0 To xdiv
		For y=0 To ydiv
			If matrix(x,y)=frm
				DrawImageRect img,x*xsize,y*ysize,x*xsize,y*ysize,xsize,ysize
			End If
		Next
	Next
Next
Delay 1000

Cls
Repeat
DrawBlock HGrundSFBN, 1,1
DrawImage Feind, 560,VFFBSF
DrawImageRect SF(g),550,VFFBSF+20, 0*SFG#, 64*SFG#, 22*SFG#, 32*SFG#
Delay 3
Flip
Cls
VFMWIEBA=VFMWIEBA+1
VFFBSF=VFFBSF+1
Until VFFBSF=650

ClsVB
ClsColor 1,255,1
Cls
Locate 0,10
SetFont Arial_30
Print "	Dein Feind hat dich entführt,"
Print "	und weit weg von deinem Zuhause in sein Nest gelegt!"
Print ""
Print "	Löse alle 20 Aufgaben um wieder"
Print "	zu deinem Haus zu gelangen."

;Hier könnte man noch wichtige sachen zum Spiel schreiben.

Print ""
Print "	Viel Glück!"
Print ""
Print ""
Print "	-Weiter mit beliebiger Taste-"
Repeat
Delay 20
vfspt=vfspt+50
ChannelPitch HGM, vfspt
Until vfspt=vfsph
WaitKey()
Goto Teil1
End









.Aufgabe_1_12_14

If Aufgabe_Aufgabenname$="Plusrechnen" Then
	Aufgabe_Operator$="+"
ElseIf Aufgabe_Aufgabenname$="Minusrechnen" Then
Aufgabe_Operator$="-"
ElseIf Aufgabe_Aufgabenname$="Malrechnen" Then
Aufgabe_Operator$="*"
Else
RuntimeError("Der Parameter "+Chr$(34)+Aufgabe_Aufgabenname$+Chr$(34)+" ist ungültig!"+Chr$(13)+"Bitte melde mir diesen Fehler per E-Mail!"+Chr$(13)+"Meine E-Mail Adresse ist "+Chr$(34)+"nico@bosshome.ch"+Chr$(34)+".")
EndIf

ClsVB
PauseChannel HGM
SpielstandSN(Aufgabe_Aufgabenname$)
Aufgabe_abgebrochen=1 : SpielstandSLR

TileBlock Aufgabenstellung_Hintergrund
Locate 0,20
SetFont Arial_100
Print " "+Aufgabe_Aufgabenname$
SetFont Arial_40
Print ""
Print "	Rechne die folgenden Rechnungen aus."
Print "	Du musst mindestens 15 von zwanzig Rechnungen richtig lösen,"
Print "	damit du weiter zur nächsten Aufgabe kommst."
Print "	Jede richtige Aufgabe ergibt 1 Belohnungsmünze."
Print "	Jede falsche gibt dagegen 1 Münze Abzug."

If Aufgabe_Operator$="*" Then

Print ""
Print "	Da bei dieser Aufgabe unabhängig der Schwierigkeitsstufe wird immer"
Print " das kleine 1*1 (nur bis 10er Reihe) abgefragt.Versuche"
Print "	doch die Aufgabe so schnell wie möglich zu lösen. Wenn du 20"
Print "	Aufgaben unter 3 Minuten(180Sekunden=durchschnittlich 9 Sekunden"
Print "	Pro Rechnung) schaffst, bekommst du als Belohnung sogar noch 5"
Print "	Belohnungsmünzen zusätzlich!"

EndIf
Print ""
Print "	Viel Glück!"
Print ""
Print ""
Print "	-Drücke eine belibige Taste um die Aufgabe zu starten-"

FreeImage HGrundH
SeedRnd MilliSecs()
WBA1=Rand (0,6)
If WBA1=0 Then HGrundH=LoadImage (".\Bilder\Wald.jpg")
If WBA1=1 Then HGrundH=LoadImage (".\Bilder\Regenbogen.jpg")
If WBA1=2 Then HGrundH=LoadImage (".\Bilder\Zugersee.jpg")
If WBA1=3 Then HGrundH=LoadImage (".\Bilder\Schmetterling.jpg")
If WBA1=4 Then HGrundH=LoadImage (".\Bilder\ZugerseeP.jpg")
If WBA1=5 Then HGrundH=LoadImage (".\Bilder\Gletscher.jpg")
If WBA1=6 Then HGrundH=LoadImage (".\Bilder\BrückeP.jpg")
WaitKey()
ClsColor 0,0,0
Cls

StartZeit = MilliSecs()

;Startvariabeln Setzen
Aufgabe_Richtig=0
Aufgabe_Falsch=0
Aufgabe_Richtig_Temp=0
Aufgabe_Falsch_Temp=0
Aufgabe_Aufgabe=0


SetFont Arial_30
Color 255,255,255

SeedRnd MilliSecs()


Aufgabe_Text_Anz=0

Origin 200,0 ;Verschiebung vom Nullpunkt um 200PX nach rechts

Repeat
;Color 255,200,0

If Aufgabe_Operator$="+" Then
	If Schwierigkeitsstufe=1 Then Zahl1= Rand(0,50) Zahl2= Rand(0,50)
	If Schwierigkeitsstufe=2 Then Zahl1= Rand(0,100) Zahl2= Rand(0,100)
	If Schwierigkeitsstufe=3 Then Zahl1= Rand(0,1000) Zahl2= Rand(0,999)
	If Schwierigkeitsstufe=4 Then Zahl1= Rand(0,9000) Zahl2= Rand(0,999) ;Zahlen sind ein bischen komisch, da das Ergebniss sollte, wegen Platzgründen, nicht über 9999 sein!
EndIf

If Aufgabe_Operator$="-" Then
	If Schwierigkeitsstufe=1 Or Schwierigkeitsstufe=2 Then Zahl1= Rand(25,50) Zahl2= Rand(0,25)
	If Schwierigkeitsstufe=2 Or Schwierigkeitsstufe=4 Then Zahl1= Rand(50,100) Zahl2= Rand(0,50)
EndIf

If Aufgabe_Operator$="*" Then
	Zahl1= Rand(0,10) Zahl2= Rand(0,10)
EndIf

Repeat

FlushKeys

Ergebnis=InputNB(Zahl1+Aufgabe_Operator$+Zahl2 + "=", "", -190, (Aufgabe_Text_Anz*71)+15, 190, 70, 5, 0, 0, 0, 0, 255, 255, 50, "|") ;Hir wird auch die Textfarbe bestimmt!
;Ergebnis=Input(Zahl1 +"+"+Zahl2 + "=")

If Aufgabe_Operator$="+" Then Aufgabe_Loesung=Zahl1+Zahl2
If Aufgabe_Operator$="-" Then Aufgabe_Loesung=Zahl1-Zahl2
If Aufgabe_Operator$="*" Then Aufgabe_Loesung=Zahl1*Zahl2

If Ergebnis=Aufgabe_Loesung Then
	Color 0,255,0
	Text -190,(Aufgabe_Text_Anz*71)+45,"Richtig!"
	Aufgabe_Richtig_Temp=1
	If Aufgabe_Falsch_Temp=0 Then Aufgabe_Richtig=Aufgabe_Richtig+1
Else
	Color 255,0,0
	Text -190,(Aufgabe_Text_Anz*71)+45,"Falsch!"
	Aufgabe_Falsch_Temp=1
EndIf

FlushKeys


Aufgabe_Text_Anz=Aufgabe_Text_Anz+1

If Aufgabe_Text_Anz=14 Then
	Aufgabe_Text_Anz=0
	Delay 1000
	Color 0,0,0
	Rect -200,0,200,1024,1
	;Cls
EndIf




;Cls
;Locate 0,0

	
VWait
Until Aufgabe_Richtig_Temp=1

Aufgabe_Falsch=Aufgabe_Falsch+Aufgabe_Falsch_Temp

Aufgabe_Richtig_Temp=0
Aufgabe_Falsch_Temp=0
Aufgabe_Aufgabe=Aufgabe_Aufgabe+1


If Aufgabe_Aufgabe=1 Then DrawImageRect HGrundH,0,0,0,0,216,256
If Aufgabe_Aufgabe=2 Then DrawImageRect HGrundH,216,0,216,0,216,256
If Aufgabe_Aufgabe=3 Then DrawImageRect HGrundH,432,0,432,0,216,256
If Aufgabe_Aufgabe=4 Then DrawImageRect HGrundH,648,0,648,0,216,256
If Aufgabe_Aufgabe=5 Then DrawImageRect HGrundH,864,0,864,0,216,256
If Aufgabe_Aufgabe=6 Then DrawImageRect HGrundH,0,256,0,256,216,256
If Aufgabe_Aufgabe=7 Then DrawImageRect HGrundH,216,256,216,256,216,256
If Aufgabe_Aufgabe=8 Then DrawImageRect HGrundH,432,256,432,256,216,256
If Aufgabe_Aufgabe=9 Then DrawImageRect HGrundH,648,256,648,256,216,256
If Aufgabe_Aufgabe=10 Then DrawImageRect HGrundH,864,256,864,256,216,256
If Aufgabe_Aufgabe=11 Then DrawImageRect HGrundH,0,512,0,512,216,256
If Aufgabe_Aufgabe=12 Then DrawImageRect HGrundH,216,512,216,512,216,256
If Aufgabe_Aufgabe=13 Then DrawImageRect HGrundH,432,512,432,512,216,256
If Aufgabe_Aufgabe=14 Then DrawImageRect HGrundH,648,512,648,512,216,256
If Aufgabe_Aufgabe=15 Then DrawImageRect HGrundH,864,512,864,512,216,256
If Aufgabe_Aufgabe=16 Then DrawImageRect HGrundH,0,768,0,768,216,256
If Aufgabe_Aufgabe=17 Then DrawImageRect HGrundH,216,768,216,768,216,256
If Aufgabe_Aufgabe=18 Then DrawImageRect HGrundH,432,768,432,768,216,256
If Aufgabe_Aufgabe=19 Then DrawImageRect HGrundH,648,768,648,768,216,256
If Aufgabe_Aufgabe=20 Then DrawImageRect HGrundH,864,768,864,768,216,256 : Delay 2000

Until Aufgabe_Aufgabe=20



Origin 0,0
Cls
Locate 0,0
FlushKeys
DrawBlock SElba, 0,0
Color 0,0,0
SetFont Arial_45
Aufgabe_abgebrochen=0 : SpielstandSLR
Text 640,50,"Du hast "+Aufgabe_Richtig+"/20 Aufgaben richtig gelöst!",True


If Aufgabe_Operator$="*" Then

	Aufgabe_Mahlrechenzeit=(MilliSecs()-StartZeit)/1000


	Text 640,550,"Zeit: "+Aufgabe_Mahlrechenzeit+" Sekunden!",True
	If Aufgabe_Mahlrechenzeit>180 Then
		Text 640,600,"Zeitziel nicht erreicht!",True
		Text 640,650,"Ist zwar überhaubt nicht schlimm aber du",True
		Text 640,700,"erhältst keine zusätzlichen Belohnungsmünzen.",True
	Else
		Text 640,600,"Zeitziel erreicht!",True
		Text 640,650,"Plus 5 Belohnungsmünzen!!!",True
		Text 640,700,"Du bist ein wahrer Turborechner!",True
		Belohnungsmuenzen=Belohnungsmuenzen+5
	EndIf

EndIf


Belohnungsmuenzen=Belohnungsmuenzen+Aufgabe_Richtig-Aufgabe_Falsch : SpielstandSLR


Text 640,850,"-Weiter mit beliebeger Taste-",True

;Belohnungsmuenzen
SpielstandSN("Punkte: "+(Aufgabe_Richtig)+"/20")
Gosub PZeit



If Aufgabe_Richtig<15 Then
	Text 640,150,"Du hast "+Aufgabe_Falsch+" Fehler gemacht",True

	If Aufgabe_Richtig>3 Then Text 640,350,"versuche die Aufgabe erneut!",True

	If Aufgabe_Richtig>10 Then ;11,12,13,14 Richtige
		Text 640,250,"Du hast leider knapp zu viele Fehler gemacht!",True
		Text 640,300,"Um die Aufgabe zu bestehen, hätten dir nur noch "+(Aufgabe_Falsch-5)+" richtige gefehlt!",True
	ElseIf Aufgabe_Richtig>6 Then ;7,8,9,10 Richtige
		Text 640,250,"Du hast leider zu viele Fehler gemacht!",True
		Text 640,300,"Um die Aufgabe zu bestehen, hätten dir noch "+(Aufgabe_Falsch-5)+" richtige gefehlt!",True
	Else ;0,1,2,3,4,5,6 Richtige
		Text 640,250,"Katastrophal:",True
		If Aufgabe_Richtig=0 Then
			Text 640,300,"Du hast alle Rechnungen falsch gelöst!!!",True
		Else
			Text 640,300,"Du hast fast alle Rechnungen falsch gelöst!!!",True
		EndIf
		Text 640,350,"Um die Aufgabe zu bestehen, hätten dir noch "+(Aufgabe_Falsch-5)+" richtige gefehlt!",True
		Text 640,400,"Ich empfehle dir dringend mehr Mathematik zu üben!",True
		Text 640,450,"Habe Geduld und versuche die Aufgabe erneut!",True
	EndIf

	WaitKey()
	

	SpielstandSN("Zu viele Fehler!")

	NAWH_Image=SElba
	Gosub NAWH
	If Aufgabe_Operator$="+" Then Goto Aufgabe1
	If Aufgabe_Operator$="-" Then Goto Aufgabe12
Else

	Text 640,150,"Herzlichen Glückwunsch:",True


	If Aufgabe_Falsch=0 Then
		Text 640,200,"Du hast alle Aufgaben fehlerfrei gemeistert!",True
		Text 640,250,"Das bist ein wahrer Mathematik Profi",True
	EndIf

	If Aufgabe_Falsch=1 Or Aufgabe_Falsch=2 Then
		Text 640,200,"Du hast ja nur "+Aufgabe_Falsch+" Fehler gemacht",True
		Text 640,250,"Das ist ja spitze!",True
	EndIf

	If Aufgabe_Falsch=3 Then
		Text 640,250,"Du hast nur "+Aufgabe_Falsch+" Fehler gemacht",True
		Text 640,200,"Für den Anfang schon ziemlich gut!",True
	EndIf

	If Aufgabe_Falsch>3 Then ;3 oder 4
		Text 640,200,"Du hast nur "+Aufgabe_Falsch+" Fehler gemacht",True
		Text 640,250,"Für den Anfang schon ziemlich gut!",True
		Text 640,300,"Du kannst dich aber noch ein bisschen verbessern.",True
	If UebersichtA=0 Then Text 640,400,"Weiter geht's zur nächsten Aufgabe!",True
EndIf


If UebersichtA=0 And Aufgabe_Falsch<4 Then Text 640,350,"Weiter geht's zur nächsten Aufgabe!",True ;Diese Befehlszeile nur ausführen bei 0,1,2 und 3 Fehler
EndIf


WaitKey()
If UebersichtA=1 Then Goto Uebersicht3
FlushKeys
;Spielstandsicherung
Aufgaben=Aufgaben+1
Goto Programmstart
End








Function GZSFG()
If G=1 Then SFG#=3
If G=2 Then SFG#=2.8
If G=3 Then SFG#=2.6
If G=4 Then SFG#=2.4
If G=5 Then SFG#=2.2
If G=6 Then SFG#=2
If G=7 Then SFG#=1.8
If G=8 Then SFG#=1.6
If G=9 Then SFG#=1.4
If G=10 Then SFG#=1.2
If G=11 Then SFG#=1
End Function




Function SXM()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 0*SFG#, 19*SFG#, 32*SFG#
Delay 175
FX=FX-4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,0*SFG#, 22*SFG#, 32*SFG#
Delay 175
FX=FX-4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#, 0*SFG#, 19*SFG#, 32*SFG#
Delay 175
FX=FX-4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 60*SFG#, 0*SFG#, 22*SFG#, 32*SFG#
Delay 175
FX=FX-4*SFG#
End Function






Function SXP()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
Delay 200
FX=FX+4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 60*SFG#, 32*SFG#, 19*SFG#, 32*SFG#
Delay 200
FX=FX+4*SFG#
End Function




Function SXPB()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-11
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-11
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-11
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 60*SFG#, 32*SFG#, 19*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-11
End Function



Function SXPB1()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-10
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-10
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-10
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 60*SFG#, 32*SFG#, 19*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY-10
End Function


Function SXPB2()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+5
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+5
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+5
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 60*SFG#, 32*SFG#, 19*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+5
End Function





Function SXPB3()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+11
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 19*SFG#,32*SFG#, 22*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+11
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#, 32*SFG#, 18*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+11
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 60*SFG#, 32*SFG#, 19*SFG#, 32*SFG#
Delay 200
FX=FX+25
FY=FY+11
End Function





Function SYP()
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 0*SFG#, 64*SFG#, 22*SFG#, 32*SFG#
Delay 175
FY=FY-4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 22*SFG#,64*SFG#, 19*SFG#, 32*SFG#
Delay 175
FY=FY-4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 41*SFG#,64*SFG#, 22*SFG#, 32*SFG#
Delay 175
FY=FY-4*SFG#
DrawBlock HGrundSF,0,0
DrawImageRect SF(g), FX, FY, 63*SFG#,64*SFG#, 22*SFG#, 32*SFG#
Delay 175
FY=FY-4*SFG#
End Function






.PZeit
JetztZeit = MilliSecs()
SpielstandSN("Zeit: "+(JetztZeit-StartZeit)/60000+"min "+(((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60)+"S")
 


;If (JetztZeit-StartZeit)/60000<>1 And ((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60<>1 Then SpielstandSN("Zeit: "+(JetztZeit-StartZeit)/60000+" Minuten "+(((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60)+" Sekunden")
;If (JetztZeit-StartZeit)/60000=1 And ((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60<>1 Then SpielstandSN("Zeit: "+(JetztZeit-StartZeit)/60000+" Minute "+(((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60)+" Sekunden")
;If (JetztZeit-StartZeit)/60000<>1 And ((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60=1 Then SpielstandSN("Zeit: "+(JetztZeit-StartZeit)/60000+" Minuten "+(((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60)+" Sekunde")
;If (JetztZeit-StartZeit)/60000=1 And ((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60=1 Then SpielstandSN("Zeit: "+(JetztZeit-StartZeit)/60000+" Minute "+(((JetztZeit-StartZeit)/1000)-((JetztZeit-StartZeit)/60000)*60)+" Sekunde")
Return





Function SpielstandSLR()
fileout = OpenFile(Name$+".txt")
	WriteLine fileout, "Aufgaben="+Trim_Zahl$(Aufgaben,3)
	WriteLine fileout, "Belohnungsmünzen="+Trim_Zahl$(Belohnungsmuenzen,6)
	WriteLine fileout, "Schwierigkeitsstufe="+Schwierigkeitsstufe
	WriteLine fileout, "Spielfigur="+Spielfigur$
	WriteLine fileout, "Games_erlauben="+Games_erlauben
	WriteLine fileout, "Aufgabe_abgebrochen="+Aufgabe_abgebrochen
	WriteLine fileout, "Protokoll (V 2.0):"
CloseFile fileout
End Function





;Schreibt nur das Protokoll neu! Eigentlicher Spielstand wird übernommen!

Function SpielstandSN(ProtokollN$)
fileout = OpenFile(Name$+".txt")
SeekFile fileout,FileSize (Name$+".txt")
WriteLine fileout, ProtokollN$
CloseFile fileout
Protokoll$=""
End Function




Function WarnungA()
ClsVB
If NNWA=0 Then
Schrift = LoadFont ("Arial",35,True)
Else
Schrift = LoadFont ("Arial",55,True)
EndIf
SetFont Schrift


K1=LoadImage (".\Bilder\Ja.jpg")
K1O=LoadImage (".\Bilder\JaO.jpg")
K2=LoadImage (".\Bilder\Nein.jpg")
K2O=LoadImage (".\Bilder\NeinO.jpg")
K1B=K1
K2B=K2
ClsColor 255,201,14
Cls
SetBuffer BackBuffer()



StartZeit = MilliSecs()
Repeat

JetztZeit = MilliSecs()

	JaO=0
	NeinO=0
	circleX=MouseX()
	circleY=MouseY()
	Cls
	
	If NNWA=0 Then Text 640,1,"Warnung:",True
	
	Text 640,35,WarnungF$,True
	

	DrawBlock K1B, 340,362
	DrawBlock K2B, 340,512
	DrawImage gfxCircle,circleX,circleY
	Flip
	If ImageRectOverlap (gfxCircle,circleX,circleY,340,362,600,150) Then K1B=K1O JaO=1 Else K1B=K1
	If ImageRectOverlap (gfxCircle,circleX,circleY,340,511,600,150) And JaO=0 Then K2B=K2O NeinO=1 Else K2B=K2
Delay 50

If MouseDown(1) And (JetztZeit-StartZeit > 1000) Then
	If  JaO=1 Or NeinO=1 Then Exit
EndIf


Forever
End Function



;Init()------------------------------------------- 

Function Init()
Restore dataSet

For i = 0 To 11 : Read mDayPerMonth( i ) : Next
For i = 0 To 7 : Read mWeekDays( i ) : Next 
End Function

;Idiv()-------------------------------------------

Function idiv( nDivident, nDivisor )
Return Floor( nDivident / nDivisor )
End Function

;LeapYear()---------------------------------------

Function LeapYear( nYear )
Return ( ( nYear And 3 ) = 0 ) And ( ( ( nYear Mod 100 ) <> 0 ) Or ( ( nYear Mod 400 ) = 0 ) )
End Function

;DaysPerMonth()-----------------------------------

Function DaysPerMonth( nYear, nMonth )
Local nResult = 0

nMonth = nMonth - 1
nResult = mDayPerMonth( nMonth )

If ( nMonth = 1 ) Then
If ( LeapYear( nYear ) ) nResult = nResult + 1
End If

Return nResult
End Function

;CheckDate----------------------------------------

Function CheckDate( nYear, nMonth, nDay )
If ( nYear < 1582 ) Return 0
If ( nYear = 1582 ) If ( nMonth < 10 ) Return 0
If ( nYear = 1582 ) If ( nMonth = 10 ) If ( nDay < 15 ) Return 0
If ( ( nMonth < 1 ) Or ( nMonth > 12 ) ) Return 0
If ( ( nDay < 1 ) Or ( nDay > DaysPerMonth( nYear, nMonth ) ) ) Return 0

Return 1
End Function 

;CalcDate-----------------------------------------

Function JulDate( nYear, nMonth, nDay )
Local nTmpYear, nTmpMonth, nResult = -1

If ( nYear = 0 ) Return nResult

If ( nMonth < 3 )
nTmpYear = nYear - 1
nTmpMonth = nMonth + 12
Else
nTmpYear = nYear
nTmpMonth = nMonth
End If

nTmpMonth = nTmpMonth + 1
nResult = nTmpYear * 365 + idiv( nTmpYear, 4 ) + idiv( ( nTmpMonth * 306 ), 10 ) + nDay + 1720995

If ( nResult > 2299170 )
nResult = nResult - idiv( nTmpYear, 100 ) + idiv( nTmpYear, 400 ) + 2 
Else If ( nResult > 2299160 )
nResult = nResult - idiv( nTmpYear, 100 ) + idiv( nTmpYear, 400 ) + 12
End If

Return nResult
End Function

;CalcDayOfWeek------------------------------------

Function CalcDayOfWeek$( nYear, nMonth, nDay )
If ( nYear < 100 )
nYear = nYear + 1900
End If

nWeekDay# = JulDate( nYear, nMonth, nDay ) Mod 7
If ( CheckDate( nYear, nMonth, nDay ) = 0 ) nWeekDay = 7

Return mWeekDays( nWeekDay )
End Function

;DataSet------------------------------------------

.dataSet
Data 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
Data "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag", "Datum ungültig"







.NAWH
ResumeChannel HGM

CLSVB
SetBuffer BackBuffer()

Const ZeitMaxNAWW=1000  ; 1 Sekunden

WHKA1=NAWHK1
WHKA2=NAWHK2
WHKA3=NAWHK3
WHKA4=NAWHK4

Repeat
circleX=MouseX()
circleY=MouseY()
NNEZKAW=0

DrawBlock NAWH_Image, 0,0
DrawBlock WHKA1, 240,312
DrawBlock WHKA2, 240,412
DrawBlock WHKA3, 240,512
DrawBlock WHKA4, 240,612
DrawImage gfxCircle,circleX,circleY
Flip
JetztZeit = MilliSecs()

If ImageRectOverlap (gfxCircle,circleX,circleY,240,312,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then Exit
WHKA1=NAWHK1O
NNEZKAW=1
Else
WHKA1=NAWHK1
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,240,412,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then AppTitle "Lernen mit Smiley" : Goto Uebersicht
WHKA2=NAWHK2O
NNEZKAW=1
Else
WHKA2=NAWHK2
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,240,512,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then AppTitle "Lernen mit Smiley" : Goto Auswahl
WHKA3=NAWHK3O
NNEZKAW=1
Else
WHKA3=NAWHK3
EndIf
If ImageRectOverlap (gfxCircle,circleX,circleY,240,612,800,100) And NNEZKAW=0 Then
If MouseDown(1) And (JetztZeit-StartZeit > ZeitMaxNA) Then AppTitle "Lernen mit Smiley" Goto EPro
WHKA4=NAWHK4O
NNEZKAW=1
Else
WHKA4=NAWHK4
EndIf
Delay 50
Forever




Function CreateFrags()
	count=Rnd(intensity)+intensity
	anstep#=360.0/count
	an#=Rnd(anstep)
	For k=1 To count
		f.Frag=New Frag
		f\x=MouseX()
		f\y=MouseY()
		f\xs=Cos( an ) * Rnd( 3,4 )
		f\ys=Sin( an ) * Rnd( 3,4 )
		f\r=255:f\g=255:f\b=255
		an=an+anstep
	Next
End Function

Function UpdateFrags()
	For f.Frag=Each Frag
		f\x=f\x+f\xs
		f\y=f\y+f\ys
		f\ys=f\ys+gravity
		If f\x<0 Or f\x>=width Or f\y>=height
			Delete f
		Else If f\b>0
			f\b=f\b-5
		Else If f\g>0
			f\g=f\g-3
		Else If f\r>0
			f\r=f\r-1
			If f\r=0 Then Delete f
		EndIf
	Next
End Function

Function RenderFrags()
	For f.Frag=Each Frag
		Color f\r,f\g,f\b
		Rect f\x-1,f\y-1,3,3
	Next
End Function








Function  Ortner_tree(Woerterdiktat_tree_Verzeichniss$)
Aufgabe_Arial_40N = LoadFont ("Arial",40,False)	;Arial  40 Normal
Aufgabe_Arial_40F = LoadFont ("Arial",40,True)		;Arial  40 Fett
SetFont Aufgabe_Arial_40N
Verz=ReadDir(Woerterdiktat_tree_Verzeichniss$)
Repeat

Datei$=NextFile$(Verz)
If Datei$= "" Then Aufgabe_Zeilen_X=Aufgabe_Zeilen_X-30 : Exit
If Not Datei$="." Or Datei$=".." Then
	Aufgabe_Zeilen_Y=Aufgabe_Zeilen_Y+50
	If Aufgabe_Zeilen_Y=1000 Then
		Aufgabe_Zeilen_Y=400
		Aufgabe_Zeilen_X=Aufgabe_Zeilen_X+430
	EndIf

;Input(Woerterdiktat_tree_Verzeichniss$+Datei$)
	If FileType(Woerterdiktat_tree_Verzeichniss$+Datei$) = 2 Then
	
		Aufgabe_Zeilen_X=Aufgabe_Zeilen_X+30
		SetFont Aufgabe_Arial_40F	;Arial 40 Fett
		Color 0,0,0
		If Len(Datei$)>20 Then
			Text Aufgabe_Zeilen_X, Aufgabe_Zeilen_Y, Left$(Datei$,20)+"..."
		Else
			Text Aufgabe_Zeilen_X, Aufgabe_Zeilen_Y, Datei$
		EndIf
		SetFont Aufgabe_Arial_40N
		Color 75,75,75
		
		Ortner_tree(Woerterdiktat_tree_Verzeichniss$+Datei$+"\")
	Else
	Aufgabe_Dateienindex$(Aufgabe_Dateienindex_Zaeler)=Woerterdiktat_tree_Verzeichniss$+Datei$
	Aufgabe_Dateienindex_Zaeler=Aufgabe_Dateienindex_Zaeler+1 ;Achtung Index ist nulbasierend! Verschiebung um -1!
	Datei$=Replace$(Datei$,".txt","")
	Datei$=Replace$(Datei$,".dat","")
	If Len(Datei$)>15 Then Datei$=Left$(Datei$,15)+"..."
		Text Aufgabe_Zeilen_X, Aufgabe_Zeilen_Y,Aufgabe_WortdateieNr+".	"+Datei$
		;Input()
		Aufgabe_WortdateieNr=Aufgabe_WortdateieNr+1
	EndIf
EndIf
Forever
CloseDir Verz

End Function





Function Trim_Zahl$(Trim_Zahl_Input, Trim_Zahl_Length)
Return String$("0", Trim_Zahl_Length-Len(Trim_Zahl_Input))+Trim_Zahl_Input
End Function




Function InputNB$(InputNB_Fragetext$, InputNB_Text$, InputNB_X, InputNB_Y, InputNB_Rect_wight, InputNB_Rect_hight, InputNB_Anz_Buchstaben, InputNB_Center, InputNB_Rect_Color_r, InputNB_Rect_Color_g, InputNB_Rect_Color_b, InputNB_Text_Color_r, InputNB_Text_Color_g, InputNB_Text_Color_b, InputNB_Curser$)
;InputNB_Fragetext$
;InputNB_Text$

;InputNB_Rect_X=20
;InputNB_Rect_Y=200
;InputNB_Rect_wight=200
;InputNB_Rect_hight=200
;InputNB_Anz_Buchstaben=10
;InputNB_Center=0

;InputNB_Rect_Color_r=255
;InputNB_Rect_Color_g=200
;InputNB_Rect_Color_b=255

;InputNB_Text_Color_r=25
;InputNB_Text_Color_g=155
;InputNB_Text_Color_b=50

;InputNB_Center=true

Code=0
InputNB_Curser_Zaeler=9

If InputNB_Center=False Then InputNB_Text_X=InputNB_X Else InputNB_Text_X=InputNB_X+(InputNB_Rect_wight/2)



Repeat

Code=GetKey()
While Not Code>0
	Delay 60
	VWait
	
	InputNB_Curser_Zaeler=InputNB_Curser_Zaeler+1
	
	If InputNB_Curser_Zaeler<10 Then	
	Color InputNB_Rect_Color_r,InputNB_Rect_Color_g,InputNB_Rect_Color_b
	Rect InputNB_X,InputNB_Y,InputNB_Rect_wight,InputNB_Rect_hight,1
	Color InputNB_Text_Color_r,InputNB_Text_Color_g,InputNB_Text_Color_b
	Text InputNB_Text_X,InputNB_Y+4,InputNB_Fragetext$+InputNB_Text$+InputNB_Curser$,InputNB_Center

	
	Else
	
	If 	InputNB_Curser_Zaeler=20 Then 	InputNB_Curser_Zaeler=0
	Color InputNB_Rect_Color_r,InputNB_Rect_Color_g,InputNB_Rect_Color_b
	Rect InputNB_X,InputNB_Y,InputNB_Rect_wight,InputNB_Rect_hight,1
	Color InputNB_Text_Color_r,InputNB_Text_Color_g,InputNB_Text_Color_b
	Text InputNB_Text_X,InputNB_Y+4,InputNB_Fragetext$+InputNB_Text$,InputNB_Center
	EndIf
	
	
	Code=GetKey()


	;Zahlenblock
	InputNB_ZBKD=0

	If KeyDown(82) Then Code=48 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(79) Then Code=49 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(80) Then Code=50 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(81) Then Code=51 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(75) Then Code=52 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(76) Then Code=53 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(77) Then Code=54 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(71) Then Code=55 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(72) Then Code=56 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1
	If KeyDown(73) Then Code=57 : InputNB_ZBNW=InputNB_ZBNW+1 : InputNB_ZBKD=1

	If InputNB_ZBNW>1 Then
			If InputNB_ZBKD=0 Then InputNB_ZBNW=0 Else Code=0
	EndIf

	InputNB_ZBKD_Alt=InputNB_ZBKD

Wend


If Code=13 Then Exit

StartZeit = MilliSecs()


If KeyDown(14)=True Or Code=8 Then
If Len(InputNB_Text$)>0 Then InputNB_Text$=Left$(InputNB_Text$,Len(InputNB_Text$)-1)

Color InputNB_Rect_Color_r,InputNB_Rect_Color_g,InputNB_Rect_Color_b
Rect InputNB_X,InputNB_Y,InputNB_Rect_wight,InputNB_Rect_hight,1
Color InputNB_Text_Color_r,InputNB_Text_Color_g,InputNB_Text_Color_b
Text InputNB_Text_X,InputNB_Y+4,InputNB_Fragetext$+InputNB_Text$,InputNB_Center


Repeat
	JetztZeit = MilliSecs()
	Delay 20
Until JetztZeit-StartZeit>750 Or KeyDown(14)=False ;Warten, bis Mehrfachlöschvorgang startet.


While KeyDown(14)=True And Len(InputNB_Text$)>0
	InputNB_Text$=Left$(InputNB_Text$,Len(InputNB_Text$)-1)
	Color InputNB_Rect_Color_r,InputNB_Rect_Color_g,InputNB_Rect_Color_b
Rect InputNB_X,InputNB_Y,InputNB_Rect_wight,InputNB_Rect_hight,1
Color InputNB_Text_Color_r,InputNB_Text_Color_g,InputNB_Text_Color_b
Text InputNB_Text_X,InputNB_Y+4,InputNB_Fragetext$+InputNB_Text$,InputNB_Center
	Delay 100 ;Zeit zwischen Löschvorgang!
Wend
EndIf

If Code<>8 And Len(InputNB_Text$)<InputNB_Anz_Buchstaben Then
	InputNB_Text$=InputNB_Text$+Chr(Code) 
EndIf

Forever




Color InputNB_Rect_Color_r,InputNB_Rect_Color_g,InputNB_Rect_Color_b
Rect InputNB_X,InputNB_Y,InputNB_Rect_wight,InputNB_Rect_hight,1
Color InputNB_Text_Color_r,InputNB_Text_Color_g,InputNB_Text_Color_b
Text InputNB_Text_X,InputNB_Y+4,InputNB_Fragetext$+InputNB_Text$,InputNB_Center
VWait


Return Trim$(InputNB_Text$) ;Eingabe wird von falschen Leerzeichen bereinigt zurückgeliefert.

End Function





Function ClsVB()
Locate 0,0
Color 0,0,0
ClsColor 0,0,0
SetBuffer BackBuffer()
Cls
SetBuffer FrontBuffer()
Cls
FlushKeys
FlushMouse
End Function
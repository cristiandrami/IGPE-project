package application.net.common;

public class Protocol {
	public final static String LOGIN= "login";
	public final static String REGISTRATION= "registration";
	public static final String GETTYPEFROMCODE = "get type from code";
	public static final String GETCLASSFROMCODE = "get class from code";
	
	//************************************ PROFESSOR *******************************//
	public final static String GETSTUDENTSFORPROF = "get students for professor";
	public static final String SENDASSIGNMENT = "send assignment";
	public static final String UPDATESTUDENTVOTE = "update student vote";
	public static final String INSERTNOTE = "insert note";
	public static final String GETCLASS = "get class";
	
	//*************************************STUDENT**********************************//
	public static final String GETVOTES = "get votes";
	public static final String GETASSIGNMENTS = "get assignments";
	public static final String GETNOTES = "get notes";
	
	
	public final static String OK= "ok";
	
	public final static String ERROR= "error during connection";
	public final static String AUTHENTICATION_ERROR= "L'username o la password sono errate, assicurati di averli inseriti correttamente";
	public final static String USER_LOGGED_ERROR= "L'utente è già loggato";
	public final static String USER_EXISTS_ERROR= "L'username è già stato scelto, riprova con uno nuovo";
	public final static String SUBJECT_ERROR= "La materia inserita non è presente nel database della scuola, riprova";
	
	

	
	


	
	

	
	
	
	
	
	


}
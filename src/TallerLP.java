/* Generated By:JavaCC: Do not edit this line. TallerLP.java */
import java.io.*;                                       //Uso de input output para archivos

public class TallerLP implements TallerLPConstants {

        /*Funcion que verifica si un caracter es numerico.
		@srtNum: se ingresa por parametro un string.
		@return: retorna true si es numerico. False es letra.	
	*/
        public static boolean isNumeric(String strNum) {
        if (strNum == null) {
                        return false;
                }
                try {
                double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
                return false;
        }
        return true;
        }

        /*Funcion que recorre un string y obtiene las cantidades numericas de dicho string.
		@linea: se ingresa por parametro un string.
		@return numero: retorna el numero obtenido de la linea de caracteres.	
	*/
        public static double obtenerCantidad(String linea){
                String numeroString = "";
                double numero = 0;
                for(int i = 0;i<linea.length();i++) {
                        String letra = String.valueOf(linea.charAt(i));
                        if(isNumeric(letra)){
                                numeroString += linea.charAt(i);
                        }
                }
                numero = Double.parseDouble(numeroString);
                return numero;
        }

        /*Funcion principal.
		@void.
	*/
        public static void main(String[] args) throws IOException {
        String LineaEntera = "";
                int[] dias = new int[30];                                                                               //Vector para almacenar las calorias diarias.
                int[] meriendas = new int[30];                                                                  //Vector que contabiliza las meriendas.
                int[] almuerzos = new int[30];                                                                  //Vector que contabiliza los almuerzos.
        for(int i = 0; i<=29;i++) {                                                                             //Iteración por los 30 dias.
                int cantidad = 0;                                                                                       //Variable para almacenar el total diario de calorias.
                int merienda = 0;                                                                                       //Variable para almacenar el total de meriendas diarias.
                int almuerzo = 0;                                                                                       //Variable para almacenar el total de almuerzos diarios.
                        try {                                                                                                           //Excepcion para controlar errores de lectura de archivo.
                    FileReader f = new FileReader("D\u00eda"+(i+1)+".txt");
                        BufferedReader bf = new BufferedReader(f);
                    String Linea = bf.readLine();
                    cantidad += obtenerCantidad(Linea);
                while(Linea != null) {
                        LineaEntera += Linea;
                        Linea = bf.readLine();
                        cantidad += obtenerCantidad(Linea);                                     //Funcion obtenerCantidad retorna las calorias por linea leida.
                        if(Linea.charAt(0) == '%') {                                            //Condicion para obtener las meriendas.
                                                merienda++;
                        }
                        if(Linea.charAt(0) == '(') {                                            //Condicion para obtener los almuerzos.
                                                almuerzo++;
                        }
                }
                        f.close();
                        }catch(Throwable e){ }
                        dias[i] = cantidad;                                                                                     //Se almacena el total de calorias en el vector aosciado a un dia.
                        meriendas[i] = merienda;                                                                        //Se almacena el total de meriendas en el vector aosciado a un dia.
                        almuerzos[i] = almuerzo;                                                                        //Se almacena el total de almuerzos en el vector aosciado a un dia.
                }
                try{
                        new TallerLP(new java.io.StringReader(LineaEntera)).sintaxis(); //Se envia todo el texto de un archivo a la funcion sintaxis para verificar mediante expresion regular.
                        System.out.println("La sintaxis es CORRECTA");
                }catch(Throwable e){                                                                                                    //Si la sintaxis no es correcta se cierra el programa.
                        System.out.println("Hay un ERROR en la sintaxis: "+ e.getMessage());
                        System.exit(0);
                }
                FileWriter calorias = null;                                                                                             //Se crea una variable para el archivo calorias y se asigna null
        PrintWriter printLinea = null;                                                                                  //Se crea una variable para las lineas del archivo calorias y se asigna null
        try{                                                                                                                                    //Excepcion para controlar errores de escritura en el archivo.
            calorias = new FileWriter("ArchivoCalorias.txt");                                   //se le asigna a la variable calorias un nuevo archivo
            printLinea = new PrintWriter(calorias);
            for (int i = 0; i <= 29; i++) {                                                                             //Iteracion para recorrer los 30 dias.
                        if(dias[i] > 0) {
                                        printLinea.println("D\u00eda(" + (i + 1) + ").");                            //Escritura en el archivo.
                    printLinea.println("TotalCalorias(" + dias[i] + ").");
                        }
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != calorias)
              calorias.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        FileWriter advertencia = null;                                                                                  //Se crea una variable para el archivo advertencias y se asigna null.
        printLinea = null;                                                                                                              //Se setea la variable printLinea a null.
        try
        {
            advertencia = new FileWriter("ArchivoAdvertencias.txt");                    //Se le asigna a la variable advertencia un nuevo archivo.
            printLinea = new PrintWriter(advertencia);                                                  //Se le asigna a la variable printLinea una linea del archivo advertencia.

            for (int i = 0; i <= 29; i++) {                                                                             //Iteracion para recorrer los 30 dias.
                if(dias[i] > 0) {                                                                                               //Condicion para calorias diarias mayores a 0 (si es 0 el dia no tiene datos)
                                        if(dias[i] > 1200 || dias[i] < 800 || almuerzos[i] == 0 || meriendas[i] > 2 || ((almuerzos[i] > 0) && (meriendas[i] > 0))) {    //Condiciones para imprimir advertencias
                                                printLinea.println("D\u00eda(" + (i + 1) + ").");
                                                if(dias[i] > 1200) {
                                                        double exceso = ((dias[i] - 1200) / 1200.0) * 100.0;
                                                        printLinea.println("Advertencia(Exceso de calor\u00edas). Excede en: " + String.format("%.1f", exceso) + "%.");
                                                }
                                                if(dias[i] < 800) {
                                                        double deficit = ((800 - dias[i]) / 800.0) * 100.0;
                                                        printLinea.println("Advertencia(Bajo en calor\u00edas). Bajo en: " + String.format("%.1f", deficit) + "%.");

                                                }
                                                if(meriendas[i] > 2) { printLinea.println("Advertencia(Se merendo "+meriendas[i]+" veces)."); }
                                                if(almuerzos[i] == 0) { printLinea.println("Advertencia(No hay almuerzo)."); }
                                                if((almuerzos[i] > 0) && (meriendas[i] > 0)) { printLinea.println("Advertencia(Almuerzo y merienda)."); }
                        }
                }
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != advertencia)
              advertencia.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

  static final public void sintaxis() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DESAYUNO:
      case ALMUERZO:
      case CENA:
      case MERIENDA:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      type();
    }
    jj_consume_token(0);
  }

  static final public void type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DESAYUNO:
      jj_consume_token(DESAYUNO);
      break;
    case ALMUERZO:
      jj_consume_token(ALMUERZO);
      break;
    case CENA:
      jj_consume_token(CENA);
      break;
    case MERIENDA:
      jj_consume_token(MERIENDA);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public TallerLPTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[2];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1e0,0x1e0,};
   }

  /** Constructor with InputStream. */
  public TallerLP(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public TallerLP(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new TallerLPTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public TallerLP(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new TallerLPTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public TallerLP(TallerLPTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(TallerLPTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 2; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[9];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 2; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 9; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}

package aplicacion;

import java.util.Scanner;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class Aplicacion {

	 public static void main( String[] args )
	    {
	    	//consulta1();
	    	//consulta2();
	    	//consulta3();
	    	//consulta4();
	    	//consulta5();
	    	consulta6();
	    }
	    
	    
	    public static void consulta1(){
	    	System.out.println("---------------------------------------------CONSULTA 1----------------------------------------------------------");
	    	System.out.println("Futbolistas que juegan en un equipo en una posición:");
	    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "adanpelayo" ) );
	    	Session session = driver.session();
	    	System.out.println("Indica el nombre del equipo:");
	    	String equipo = ReadString();
	    	System.out.println("Indica la posición:");
	    	String posicion = ReadString();
	    	
	    	StatementResult sr = session.run("MATCH (m:Futbolista)-[:JUEGA_EN]->(n:Equipo)" +
	    									" WHERE n.NombreEquipo= {1} and m.Demarcación={2}" + 
	    									" RETURN m.Nombre, m.Apellidos, m.Edad, m.Demarcación, m.Nacionalidad, m.AlturaCm, m.PesoKg",
	    									parameters("1",equipo, "2", posicion));
	    	System.out.println("---RESULTADO---");
	    	while ( sr.hasNext() )
	    	{
	    	    Record record = sr.next();
	    	    System.out.println("Nombre: "+record.get( "m.Nombre" ).asString() + "  Apellido: " + record.get( "m.Apellidos" ).asString() +"  Edad: "+
	    	    record.get("m.Edad").asInt()+"  Demarcación: "+record.get("m.Demarcación").asString()+"  Nacionalidad: "+record.get("m.Nacionalidad").asString() +
	    	    "  Altura en cm: "+record.get("m.AlturaCm").asInt()+"  Peso en kg: "+record.get("m.PesoKg").asInt());
	    	}

	    	session.close();
	    	driver.close();
	    	System.out.println();
	    	System.out.println();
	    }
	    
	    public static void consulta2(){
	    	System.out.println("---------------------------------------------CONSULTA 2----------------------------------------------------------");
	    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "adanpelayo" ) );
	    	Session session = driver.session();
	    	System.out.println("Mostrar las competiciones de un pais que tienen más de un numero de equipos y duran x meses ordenadas por nº de equipos desc:");
	    	System.out.println("Indica el nombre del país:");
	    	String pais = ReadString();
	    	System.out.println("Indica el número de equipos:");
	    	int equipos = ReadInt();
	    	System.out.println("Indica el número de meses:");
	    	int meses = ReadInt();
	    
	    	StatementResult sr = session.run("MATCH (c:Competicion)-[:ESTA_EN]->(p:Pais)"
	    									+ " WHERE p.NombrePais={1} AND c.numeroEquipos>{2} AND c.duracionMeses={3}"
	    									+ " RETURN c.NombreCompeticion, c.numeroEquipos ORDER BY c.numeroEquipos DESC",
	    									parameters("1",pais,"2",equipos,"3",meses));
	    	System.out.println("---RESULTADO---");
	    	while ( sr.hasNext() )
	    	{
	    	    Record record = sr.next();
	    	    System.out.println("Nombre de la competición: "+ record.get( "c.NombreCompeticion" ).asString() + "  Número de equipos: " + record.get("c.numeroEquipos").asInt());
	    	}

	    	session.close();
	    	driver.close();
	    	System.out.println();
	    	System.out.println();
	    }
	    
	    public static void consulta3(){
	    	System.out.println("---------------------------------------------CONSULTA 3----------------------------------------------------------");
	   
	    	System.out.println("Calcular la edad media de los futbolistas agrupados que juegan en una liga y mostrando ésta y el número de futbolistas que la componen:");
	    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "adanpelayo" ) );
	    	Session session = driver.session();
	    	System.out.println("Indica el nombre de la liga:");
	    	String liga = ReadString();
	    	
	    	StatementResult sr = session.run("MATCH (m:Futbolista)-[:JUEGA_EN]->(n:Equipo)-[:ESTA_EN]->(p:Competicion)"
	    								+ " WHERE p.NombreCompeticion={1}"
	    								+ " RETURN avg(m.Edad) as EdadMedia, count(*) as NúmeroFutbolistas",parameters("1",liga));
	    	System.out.println("---RESULTADO---");
	    	while ( sr.hasNext() )
	    	{
	    	    Record record = sr.next();
	    	    System.out.println("Edad Media: "+ record.get( "EdadMedia" ).asDouble() + "  Número de futbolistas: " + record.get("NúmeroFutbolistas").asInt());
	    	}

	    	session.close();
	    	driver.close();
	    	System.out.println();
	    	System.out.println();
	    }
	    
	    public static void consulta4(){
	    	System.out.println("---------------------------------------------CONSULTA 4----------------------------------------------------------");
	    	
	    	System.out.println("Mostrar los jugadores extranjeros de un pais que jueguen en equipos y compitan en competiciones de un pais y que no hayan ganado títulos");
	    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "adanpelayo" ) );
	    	Session session = driver.session();
	    	System.out.println("Indica el nombre del país: ");
	    	String pais = ReadString();
	    	System.out.println("Indica la nacionalidad de los jugadores:");
	    	String nacionalidad = ReadString();
	    	
	    	StatementResult sr = session.run("MATCH (m:Futbolista)-[:JUEGA_EN]->(n:Equipo)-[:ESTA_EN]->(c:Competicion)-[:ESTA_EN]->(p:Pais)"
	    								+ " WHERE p.NombrePais={1}  and not m.Nacionalidad={2} and not((n)-[:GANO]->()) "
	    								+ " RETURN distinct m.Nombre, m.Apellidos", parameters("1", pais, "2", nacionalidad));
	    	System.out.println("---RESULTADO---");
	    	while ( sr.hasNext() )
	    	{
	    	    Record record = sr.next();
	    	    System.out.println( record.get( "m.Nombre" ).asString() + " " + record.get("m.Apellidos").asString());
	    	}

	    	session.close();
	    	driver.close();
	    	System.out.println();
	    	System.out.println();
	    }
	    
	    public static void consulta5(){
	    	System.out.println("---------------------------------------------CONSULTA 5----------------------------------------------------------");
	    	
	    	System.out.println("Obtener parejas de futbolistas que juegan en el mismo equipo, en posiciones diferentes, con distintas edades y de la misma nacionalidad");
	    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "adanpelayo" ) );
	    	Session session = driver.session();
	    
	    	StatementResult sr = session.run("MATCH (m:Futbolista)-[:JUEGA_EN]->(e:Equipo)<-[:JUEGA_EN]-(m2:Futbolista)"
	    			+ " WHERE NOT ((m) = (m2)) AND NOT (m.Demarcación=m2.Demarcación) AND (m.Nacionalidad=m2.Nacionalidad) AND NOT (m.Edad=m2.Edad)"
	    			+ " RETURN distinct  m.Nombre, m2.Nombre");
	    	System.out.println("---RESULTADO---");
	    	while ( sr.hasNext() )
	    	{
	    	    Record record = sr.next();
	    	    System.out.println( record.get( "m.Nombre" ).asString() + " " + record.get("m2.Nombre").asString());
	    	}

	    	session.close();
	    	driver.close();
	    	System.out.println();
	    	System.out.println();
	    }
	    
	    public static void consulta6(){
	    	System.out.println("---------------------------------------------CONSULTA 6----------------------------------------------------------");
	    	
	    	System.out.println("Obtener parejas de futbolistas que juegan o que hayan jugado en el mismo equipo, que tengan edades diferentes, que no jueguen en la misma posición y que no hayan ganado nada.");
	    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "adanpelayo" ) );
	    	Session session = driver.session();
	    
	    	StatementResult sr = session.run("MATCH (a:Futbolista)-[:JUGO_EN]->(b:Equipo)<-[:JUEGA_EN]-(c:Futbolista)"
	    			+ " WHERE not (a.Edad = c.Edad) and not(a.Demarcación = c.Demarcación) and not ((b)-[:GANO]->())	"
	    			+ " RETURN a.Nombre, c.Nombre");
	    	System.out.println("---RESULTADO---");
	    	while ( sr.hasNext() )
	    	{
	    	    Record record = sr.next();
	    	    System.out.println( record.get( "a.Nombre" ).asString() + " " + record.get("c.Nombre").asString());
	    	}

	    	session.close();
	    	driver.close();
	    	System.out.println();
	    	System.out.println();
	    }
	    
	    @SuppressWarnings("resource")
		private static String ReadString(){
			return new Scanner(System.in).nextLine();		
		}
		
		@SuppressWarnings("resource")
		private static int ReadInt(){
			return new Scanner(System.in).nextInt();			
		}	
	
}

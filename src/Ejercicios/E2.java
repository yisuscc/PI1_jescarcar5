package Ejercicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.Pair;
import us.lsi.streams.Stream2;

public class E2 {




	// versión recursiva no final
	public static Integer Ej2RecNF(Integer a, Integer b, String s) {
		return Ej2RecNFAux(a, b, s);

	}
	private static Integer Ej2RecNFAux(Integer a, Integer b, String s){
		//		if(a<0|| b<0) {
		//			throw new IllegalArgumentException("A y b tienen que ser positivos");
		//		}
		Integer r = null; 
		if(0<=a|| 0<=b){
			if(s.length()==0) {
				r = a*a+b*b;
			}
			else if (a<2 || b<2){
				r = s.length()+a+b;
			}
			else if (a%s.length()<b%s.length()) {
				r= a+b+Ej2RecNFAux(a-1, b/2, s.substring(a%s.length(), b%s.length()));
			}			 

			else {
				r = a*b+Ej2RecNFAux(a/2, b-1, s.substring(b%s.length(), a%s.length()));

			}

		}
		return r;


	}
	// version recursiva  final 
	public static Integer Ej2RecFinal(Integer a, Integer b, String s) {
		return Ej2RecFinalAux(a, b, s, 0);

	}
	private static Integer Ej2RecFinalAux(Integer a, Integer b, String s,Integer acum) {
		Integer r = null; 
		if(0<=a|| 0<=b){
			if(s.length()==0) {
				r = a*a+b*b +acum;
			}
			else if (a<2 || b<2){
				r = s.length()+a+b+ acum;
			}
			else if (a%s.length()<b%s.length()) {
				acum = a+b +acum;
				r= Ej2RecFinalAux(a-1, b/2, s.substring(a%s.length(), b%s.length()),acum);
			}			 

			else {
				acum = a*b +acum;
				r = Ej2RecFinalAux(a/2, b-1, s.substring(b%s.length(), a%s.length()),acum);
			}


		}
		return r;

	}

	//versión iterativa  while
	public static Integer Ej2Iterativo(Integer a, Integer b, String s) {

		Integer r;

		Trio res  = Trio.of(a,b,s.length());
		Map<Trio, Integer> d = new HashMap<>();
		// con for queda mucho mas bonito y mejor 
		Integer i = 0;  // se encarga de a
		// se encarga de b
		// se encarga de s
		Integer sl = s.length();
		///parece que al inicio de cada bucle las variables no se resetean
		while(i<=a){
			Integer j =0; 
			while(j<=b){ 
				Integer k = 0; 
				while(k<=sl) {

					if(k==0) {
						r = i*i+j*j;
						d.put(Trio.of(i, j, k), r);
					}
					else if (i<2 || j<2){
						r = k+i+j;
						d.put(Trio.of(i, j, k), r);
					}
					else if (i%k<j%k) {
						Integer n = j%k- i%k;
						r= i+j+d.get(Trio.of(i-1, j/2, n));
						d.put(Trio.of(i, j, k), r);
					}			 

					else {
						Integer n = i%k- j%k;
						r = i*j+d.get(Trio.of(i/2, j-1, n));
						d.put(Trio.of(i, j, k), r);

					}
					k++;

				}
				j++;
			}
			i++;
		}
		return d.get(res);

	}

	//iterativo for 
	public static Integer Ej2IterativoFor(Integer a, Integer b, String s) {

		Trio res  = Trio.of(a,b,s.length());
		Map<Trio, Integer> d = new HashMap<>();
		for(Integer i = 0;i<=a;i++) {
			for(Integer j=0;j<=b;j++) {
				for(Integer k= 0;k<=s.length();k++) {
					if(k==0) {
						Integer r = i*i+j*j;
						d.put(Trio.of(i, j, k), r);
					}
					else if (i<2 || j<2){
						Integer r = k+i+j;
						d.put(Trio.of(i, j, k), r);
					}
					else if (i%k<j%k) {
						Integer n = j%k- i%k;
						Integer r= i+j+d.get(Trio.of(i-1, j/2, n));
						d.put(Trio.of(i, j, k), r);
					}			 

					else {
						Integer n = i%k- j%k;
						Integer r = i*j+d.get(Trio.of(i/2, j-1, n));
						d.put(Trio.of(i, j, k), r);
					}
				}
			}
		}
		return d.get(res);
	}
	// versiónn funcional
	public static Integer Ej2FuncionalV1(Integer a, Integer b, String s) {
		// version traducida directa del iterativo, 
		Trio res  = Trio.of(a,b,s.length());// uso mi propio record de trio 
		Map<Trio, Integer> d = new HashMap<>();
		Integer sl = s.length();
		Map<Trio, Integer> mapAux = new HashMap<>();
		Consumer<Trio> con = x-> {
			Integer r = null; 
			Integer i = x.a();
			Integer j = x.b();
			Integer k = x.c();

			if(k==0) {
				r = i*i+j*j;
				d.put(Trio.of(i, j, k), r);// se puede sutstituir el trio.of() por x 
			}
			else if (i<2 || j<2){
				r = k+i+j;
				d.put(Trio.of(i, j, k), r);
			}
			else if (i%k<j%k) {
				Integer n = j%k- i%k;
				r= i+j+d.get(Trio.of(i-1, j/2, n));
				d.put(Trio.of(i, j, k), r);
			}			 

			else {
				Integer n = i%k- j%k;
				r = i*j+d.get(Trio.of(i/2, j-1, n));
				d.put(Trio.of(i, j, k), r);

			}

		};
		Trio.fullSequence(a, b, s.length()).stream().forEach(con);
		return d.get(res);

	}
	
	public static Integer Ej2FuncionalV2(Integer a, Integer b, String s) {
		// Similar al anterior 
		Map<Trio, Integer> d = new HashMap<>();
		
		Function<Trio,Pair<Trio, Integer>> con = x-> {
			Integer r = null; 
			Integer i = x.a();
			Integer j = x.b();
			Integer k = x.c();

			if(k==0) {
				r = i*i+j*j;
				d.put(x, r);
			}
			else if (i<2 || j<2){
				r = k+i+j;
				d.put(Trio.of(i, j, k), r);
			}
			else if (i%k<j%k) {
				Integer n = j%k- i%k;
				r= i+j+d.get(Trio.of(i-1, j/2, n));
				d.put(x, r);
			}			 

			else {
				Integer n = i%k- j%k;
				r = i*j+d.get(Trio.of(i/2, j-1, n));
				d.put(x, r);

			}
			return Pair.of(x, r);
		};
		Predicate<Pair<Trio, Integer>> prd = p -> p.first().equals(Trio.of(a, b, s.length())); 
		 return Stream.iterate(Trio.of(0, 0, 0),T-> T.hasNext(a, b, s.length()),T->T.nextTrio(a, b, s.length())).map(t -> con.apply(t)).filter(prd).findFirst().get().second();
	}
	public static Integer Ej2FuncionalV3(Integer a, Integer b, String s) {
		//este requiere inicializar el map 
		//la version anterior no 
		
		Integer sl = s.length();
		Map<Trio, Integer> d = new HashMap<>();
		//d.put(Trio.of(0,0,0), 0); N va a hacer falta creo yo  pero lo dejo comentado por si acaso 
		UnaryOperator<Pair<Trio, Integer>> con = y-> {
			Trio x = y.first().nextTrio(a, b, sl);
			Integer r = null; 
			Integer i = x.a();
			Integer j = x.b();
			Integer k = x.c();

			if(k==0) {
				r = i*i+j*j;
				d.put(x, r);
			}
			else if (i<2 || j<2){
				r = k+i+j;
				d.put(Trio.of(i, j, k), r);
			}
			else if (i%k<j%k) {
				Integer n = j%k- i%k;
				r= i+j+d.get(Trio.of(i-1, j/2, n));
				d.put(x, r);
			}			 

			else {
				Integer n = i%k- j%k;
				r = i*j+d.get(Trio.of(i/2, j-1, n));
				d.put(x, r);

			}
			return Pair.of(x, r);
		};
		
		Predicate<Pair<Trio, Integer>> prd = p -> p.first().equals(Trio.of(a, b, s.length()));
		
		return Stream.iterate(Pair.of(Trio.of(0, 0, 0),0 ), p1-> p1.first().hasNext(a, b, sl),p -> con.apply(p)).
				filter(prd).findFirst().get().second();
	}



}

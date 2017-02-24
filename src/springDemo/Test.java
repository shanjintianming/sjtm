package springDemo;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Random rand = new Random();
		int i = rand.nextInt();
		int j = rand.nextInt();
		
		System.out.println( "i" + ":  " + i);
		System.out.println( "~i" + ": " + ~i);
		System.out.println( "i" + ":  " + toFullBinaryString(i));
		System.out.println( "~i" + ": " + toFullBinaryString(~i));
		
		long l = -2;
		
		System.out.println( "l" + ": " + toFullBinaryString(l));
		
		l>>=10;
		
		System.out.println( "l" + ": " + toFullBinaryString(l));
		System.out.println( "l" + ": " + l);
		
		
		label1:
			for(int t=0;t<2;t++){
				for(int h=0;h<2;h++){
					if (t == 1) {
						break label1;
					}				
				}
				System.out.println(t);
			}
		
	}
	
	 /**
     * 将 int 类型数据转成二进制的字符串，不足 int 类型位数时在前面添“0”以凑足位数
     * @param num
     * @return
     */
    public static String toFullBinaryString(int num) {
        char[] chs = new char[Integer.SIZE];
        for(int i = 0; i < Integer.SIZE; i++) {
            chs[Integer.SIZE - 1 - i] = (char)(((num >> i) & 1) + '0');
        }
        return new String(chs);        
    }
    
    /**
     * 将 long 类型数据转成二进制的字符串，不足 long 类型位数时在前面添“0”以凑足位数
     * @param num
     * @return
     */
    public static String toFullBinaryString(long num) {
        char[] chs = new char[Long.SIZE];
        for(int i = 0; i < Long.SIZE; i++) {
            chs[Long.SIZE - 1 - i] = (char)(((num >> i) & 1) + '0');
        }
        return new String(chs);        
    }

    
}

class Bolwl {
	public Bolwl(int i){
		System.out.println("Bolwl(" + i +")");
	}
	
	void f(int i){
		System.out.println("f(" + i +")");
	}
}

class Tabel {
	static Bolwl b1 = new Bolwl(1);
	public Tabel(int i){
		System.out.println("Tabel()");
		b2.f(1);
	}
	
	void f2(int i){
		System.out.println("f2(" + i +")");
	}
	static Bolwl b2 = new Bolwl(1);
}

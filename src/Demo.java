
public class Demo {

	public static void main(String[] args) {
		String packageName = "com.pailian";
		
		String result = packageName.replaceAll("\\.", "/");
		System.out.println(result);
	}
}

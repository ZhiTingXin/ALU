package cn.edu.nju.software151250171;
/**
 * ģ��ALU���������͸���������������
 * @author [��151250171_���쿡�]
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		String str=new String();
	    int a=Integer.parseInt(number);
	    int b=Math.abs(a);//����ֵ
	    //�����������Ƶ�һ���㷨
	    while(b!=0){
            str= (b%2)+str;
            b=b/2;
        }
	    //����,�����λ
	    if(a>=0){
	    	for(int i=str.length();i<length;i++){
	    		str="0"+str;
	    	}
	    }
	    //������ȡ����һ�������λ
	    else{
	    	str=this.negation(str);
	    	str=this.oneAdder(str);
	    	str=str.substring(1,str.length());
	    	for(int i=str.length();i<length;i++){
	    		str="1"+str;
	    	}
	    }		
		return str;
	}
		/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String str=new String();
		String zhengshu=new String();
		String xiaoshu=new String();
		String fraction=new String();
		String exponent=new String();
		//0�����
		if(number=="0"){
			for(int i=0;i<1+eLength+sLength;i++){
				str="0"+str;
			}
		}
		int u=0;
		char[] newnumber=number.toCharArray();
		for(int m=0;m<newnumber.length;m++){
			if(newnumber[m]=='.'){
				u=u+1;
			}
		}
		if(u!=0){
		String[] yuanshuzhi=number.split("[.]");
		//������С����ʮ���Ʊ�ʾ
		int zs=0;
		if(number.charAt(0)!='-'){
			zs=Integer.parseInt(yuanshuzhi[0]);
		}
		else{
			zs=Integer.parseInt(String.valueOf(yuanshuzhi[0]).substring(1));
		}
		Double xs=Double.parseDouble("0."+yuanshuzhi[1]);
		//�������ֵĶ�����ԭ���ʾ
		while(zs!=0){
			zhengshu=(zs%2)+zhengshu;
			zs=zs/2;
		}
		//С�����ֵĶ����Ʊ�ʾ
		while(xs!=0){
			xs=xs*2;
			if(xs>=1){
				xiaoshu=xiaoshu+"1";
				xs=xs-1;
			}
			else{
				xiaoshu=xiaoshu+"0";
			}
		}
		fraction=zhengshu+xiaoshu;
		String newfra=new String();
		int ex=0;
		ex=-xiaoshu.length();
		char[] fra=fraction.toCharArray();
		for(int i=0;i<fra.length;i++){
			if(fra[i]=='1'){
				for(int j=i+1;j<fra.length;j++){
					newfra=newfra+String.valueOf(fra[j]);
					ex=ex+1;
				}
				break;
			}
		}
		//ָ��
		ex=ex+127;
		int ex1=ex;
		//β��
		while(newfra.length()<sLength){
			newfra=newfra+"0";
		}
		//�������
		if(ex<-Math.pow(2,eLength-1)+1){
			newfra="1"+newfra.substring(0,newfra.length()-1);
			ex=ex+1;
		}
		while(ex<-Math.pow(2,eLength-1)+1){
			newfra=logRightShift(newfra,1);
			ex=ex+1;
		}
		//�����
		 while(ex!=0){
			exponent=(ex%2)+exponent;
			ex=ex/2;
		}
		while(exponent.length()<eLength){
			exponent="0"+exponent;
		}
		str=exponent+newfra;
		
		//��ӷ���λ
				if(number.charAt(0)=='-'){
					str="1"+str;
				}
				else{
					str="0"+str;
				}
				//����
		if(ex1>Math.pow(2,eLength)-1){
			if(number.charAt(0)=='-'){
				str="+Inf";
			}
			else{
				str="-Inf";
			}
			}
		}
		return str;
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		String str = new String();
		if(length == 32 ){
			str = floatRepresentation(number,8,23);
		}
		else{
			str = floatRepresentation(number,11,52);
		}
		return str;
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		char[] ch1=operand.toCharArray();
		double number=0;
		String str=null;
		if(Integer.parseInt(String.valueOf(ch1[0]))==0){//��������
			for(int i=1;i<ch1.length;i++){
					int a=Integer.parseInt(String.valueOf(ch1[i]));
				number=number+a*Math.pow(2,ch1.length-i-1);
			}
			int number1=(int)number;
			str=Integer.toString(number1);
		}
		else{
			number=(Math.pow(2,ch1.length-1));
			for(int i=1;i<ch1.length;i++){
				int a=Integer.parseInt(String.valueOf(ch1[i]));
				number=number-a*Math.pow(2,ch1.length-i-1);
			}
			int  number1=(int)number;
			str=String.valueOf(number1);
			char[] ch2=str.toCharArray();
			char[] ch3=new char[ch2.length+1];
			ch3[0]='-';
			for(int j=1;j<ch2.length+1;j++){
				ch3[j]=ch2[j-1];
			}
			str=String.valueOf(ch3);
		}
		return str;
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		boolean isnagetive = false;
		if(operand.charAt(0)=='1'){
			isnagetive = true;
		}
		String exponent = operand.substring(1,eLength+1);
		String fraction = operand.substring(eLength+1);
		String ex0=new String();
		String ex1=new String();
		String fr =new String();
		for(int i=eLength;i>=1;i--){
			ex0=ex0+"0";
		}
		for(int i=eLength;i>=1;i--){
			ex1=ex1+"1";
		}
		for(int i=sLength;i>=1;i--){
			fr=fr+"0";
		}
		//0
		if(exponent.equalsIgnoreCase(ex0)&&fraction.equalsIgnoreCase(fr)){
			return "0";
		}
		//����
		if(exponent.equalsIgnoreCase(ex1)&&fraction.equalsIgnoreCase(fr)){
			if(isnagetive){
				return "-Inf";
			}
			else{
				return "+Inf";
		}
		}
		//����
		if(exponent.equalsIgnoreCase(ex1)&&(!fraction.equalsIgnoreCase(fr))){
			return "NaN";
		}
		double result=0;
		//ָ����ֵ
		int e1 = 0;
		for(int i=eLength-1;i>=0;i--){
			if(exponent.charAt(i)=='1'){
				e1 = e1 + (int)Math.pow(2,eLength-1-i);
		}
		}
		e1 = e1 - (int)(Math.pow(2,eLength-1)-1);
		//�����
		if(exponent.equalsIgnoreCase(ex0)&&!fraction.equalsIgnoreCase(fr)){
			for(int i=0;i<=fraction.length()-1;i++){
				if(fraction.charAt(i)=='1'){
					result = result + Math.pow(2,-(i+1));
			}
			}
			result = result * Math.pow(2,e1+1);
			if(isnagetive){
				return "-"+result;
			}
			else{
				return result+"";
		}
		}
		//���
		if(e1>=1){//Ҫ�ֳ�������С��
			String part1;//����
			String part2;//С��
			part1="1"+fraction.substring(0,e1);
			part2=fraction.substring(e1);
			for(int i=part1.length()-1;i>=0;i--){
				if(part1.charAt(i)=='1'){
					result = result + Math.pow(2,part1.length()-1-i);
			}
			}
			for(int i=0;i<=part2.length()-1;i++){
				if(part2.charAt(i)=='1'){
					result = result + Math.pow(2,-(i+1));
			}
			}
		}
		else{
			for(int i=0;i<=fraction.length()-1;i++){
				if(fraction.charAt(i)=='1'){
					result = result + Math.pow(2,-(i+1));
			}
			}
			result = result + 1;
			result = result * Math.pow(2,e1);
		}
		if(isnagetive){
			return "-"+result;
		}
		else{
			return result+"";
	}
	}
	
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		char [] ch=operand.toCharArray();
		for(int j=0;j<ch.length;j++){
			if(ch[j]=='0'){
				ch[j]='1';
			}
			else{
				ch[j]='0';
			}
		}
		String str=String.valueOf(ch);
		return str;
	}
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		char[] ch1=operand.toCharArray();
		char[] ch2=new char[ch1.length];
		for(int i=0;i<ch1.length-n;i++){
			ch2[i]=ch1[i+n];
		}
		for(int j=ch1.length-n;j<ch1.length;j++){
			ch2[j]='0';//���Ƽ�0
		}
		String str=String.valueOf(ch2);
		return str;
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		char[] ch1=operand.toCharArray();
		char[] ch2=new char[ch1.length];
		for(int i=0;i<n;i++){
			ch2[i]='0';//�߼����Ƽ�0
		}
		for(int j=n;j<ch1.length;j++){
			ch2[j]=ch1[j-n];
		}
		String str=String.valueOf(ch2);
		return str;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		char[] ch1=operand.toCharArray();
		char[] ch2=new char[ch1.length];
		for(int j=n;j<ch1.length;j++){
			ch2[j]=ch1[j-n];
		}
		for(int i=0;i<n;i++){
			if(ch1[0]=='0'){
				ch2[i]='0';
			}
			else{
				ch2[i]='1';//�������Ƽӷ���λ
			}
		}
		String str=String.valueOf(ch2);
		return str;
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		int sum=Integer.parseInt(String.valueOf(x))+Integer.parseInt(String.valueOf(y))+Integer.parseInt(String.valueOf(c));
		char[] ch=new char[2];
		//ö���������
		if(sum==0){
			ch[0]='0';
			ch[1]='0';
		}
		else if(sum==1){
			ch[0]='0';
			ch[1]='1';
		}
		else if(sum==2){
			ch[0]='1';
			ch[1]='0';
		}
		else if(sum==3){
			ch[0]='1';
			ch[1]='1';
		}
		String str=String.valueOf(ch);	
		return str;
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		char[] ch2=operand2.toCharArray();
		char[] ch1=operand1.toCharArray();
		char[] ch=new char[2];
		String str=new String();
		for(int i=3;i>=0;i--){
		String str1=this.fullAdder(ch1[i],ch2[i], c);
			ch=str1.toCharArray();
			c=ch[0];
			str=String.valueOf(ch[1])+str;
		}//�����Ĵ�
		str=String.valueOf(ch[0])+str;//���Ͻ�λ
		return str;
		
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		char[] ch=operand.toCharArray();
		String str=new String();
		//����Ŀ����ԣ�01111111~+1=100000~
		String str1=new String();
		for(int i=1;i<ch.length;i++){
			str1="1"+str1;
		}
		str1="0"+str1;
		if(operand.equals(str1)){
			ch[0]='1';
			for(int j=1;j<ch.length;j++){
				ch[j]='0';
			}
			str="1"+String.valueOf(ch);
		}//ֻ�����ֿ������
		else{
			for(int k=ch.length-1;k>=0;k--){
				if(ch[k]=='1'){
					ch[k]='0';
				}
				else{
					ch[k]='1';
					break;
				}
			}
			str="0"+String.valueOf(ch);
		}
		return str;
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		//������չ
		char[] ch1=operand1.toCharArray();
		char[] ch2=operand2.toCharArray();
		String str1=new String();
		String str2=new String();
		String str=new String();
		if(ch1.length<length){
			for(int i=0;i<length-ch1.length;i++){
				str1=String.valueOf(ch1[0])+str1;
			}	
		}
		str1=str1+operand1;
		if(ch2.length<length){
			for(int i=0;i<length-ch2.length;i++){
				str2=String.valueOf(ch2[0])+str2;
			}
		}
		str2=str2+operand2;
	//�ֳ�ÿ��λһ�飬������λ���мӷ�
		int a=length/4;//a����
		for(int i=a;i>=1;i--){
		String str11=str1.substring(4*i-4,4*i);
		String str22=str2.substring(4*i-4,4*i);
		String str33=this.claAdder(str11,str22, c);
		char[] ch3=str33.toCharArray();
		c=ch3[0];
		str=str33.substring(1,5)+str;
		}
		//�õ���length���ȵ�str���������ж��Ƿ����
		//����IntegerTrueValue,�ж��Ƿ��ڱ�ʾ��Χ��
		String s1=this.integerTrueValue(str1);
		String s2=this.integerTrueValue(str2);
		int m=Integer.parseInt(s1);
		int n=Integer.parseInt(s2);
		if((m+n)>=Math.pow(2,length-1)-1||(m+n)<-Math.pow(2,length-1)){
			str="1"+str;
		}
		else{
			str="0"+str;
		}
				return str;
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String str=this.adder(operand1,operand2,'0',length);
		return str;
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String str=adder(operand1,negation(operand2),'1',length);
		return str;
		
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		//����λ��չ
		while(operand1.length()<length){
			operand1 = operand1.charAt(0)+operand1;
			}
		while(operand2.length()<length){
			operand2 = operand2.charAt(0)+operand2;
			}
		String str1 = operand2 + "0";//����ĩβ��0
		String str2 =new String() ;
		for(int i=length;i>=1;i--)
			str2 = str2 + "0";//A�Ĵ�����ʼ��
		String str3 = operand2;//Q�Ĵ�����ʼ��
		String str = str2 + str3;
		for(int i=length;i>=1;i--){//����length��
			int temp = str1.charAt(i)-str1.charAt(i-1);
			str2 = str.substring(0,length);
			str3 = str.substring(length);
			if(temp==1){
				str2 =adder(str2,operand1,'0',length).substring(1);//0-1 ��
			}else if(temp==-1){
				str2 = integerSubtraction(str2,operand1,length).substring(1);//1-0��
			}
			str = str2+str3;
			str = ariRightShift(str,1);//��������
		}
		str=str.substring(length);
		//�ж����
		long a=Long.parseLong(integerTrueValue(str));
		long b=Long.parseLong(integerTrueValue(operand2))*Integer.parseInt(integerTrueValue(operand1));
		
		if(a==b){
			str="0"+str;
		}
		else{
			str="1"+str;
		}
		return str;
	}
	
	/**
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		//����λ��չ
		int x=0;
		while(operand1.length()<length){
			operand1 = operand1.charAt(0)+operand1;
			}
		while(operand2.length()<length){
			operand2 = operand2.charAt(0)+operand2;
			}
		String remainder=new String();
		for(int i=0;i<length;i++){
			remainder=remainder+operand1.charAt(0);
		}//�����Ĵ�����ʼ��
		String quotient=operand1;//�̼Ĵ�����ʼ��
		String dividend=remainder+quotient;//��������ʼ��
		String divisor=operand2;//������ʼ��
		//��һ�μӼ�
		if(dividend.charAt(0)==divisor.charAt(0)){
			remainder=integerSubtraction(remainder,divisor, length).substring(1);
		}
		else{
			remainder=integerAddition(remainder,divisor, length).substring(1);
		}//����������ͳ�������ͬ���ţ������������������
		dividend=remainder+quotient;
		if(remainder.charAt(0)==divisor.charAt(0)){
			x=1;
		}
		else{
			x=0;
		}
		//length����λ+�Ӽ�
		for(int i=0;i<length;i++){
			if(remainder.charAt(0)==divisor.charAt(0)){
				dividend=dividend.substring(1)+"1";
			}
			else{
				dividend=dividend.substring(1)+"0";
			}//������������ͬ�ţ������һλ׼����1��������0,���ƣ������һλ���ϣ�
			remainder=dividend.substring(0,length);
			quotient=dividend.substring(length);
			if(dividend.charAt(0)==divisor.charAt(0)){
				remainder=integerSubtraction(remainder,divisor, length).substring(1);
			}
			else{
				remainder=integerAddition(remainder,divisor, length).substring(1);
			}//����������ͳ�������ͬ���ţ������������������
			dividend=remainder+quotient;
			}
		//����
		if(remainder.charAt(0)==divisor.charAt(0)){
			quotient=quotient.substring(1)+"1";
		}
		else{
			quotient=quotient.substring(1)+"0";
		}//��������������Ĺ�ϵ�����Ʋ����������һλ
		if(operand1.charAt(0)!=operand2.charAt(0)){
			quotient=oneAdder(quotient).substring(1);
		}//����������������ţ���Ҫ����һ
		if(remainder.charAt(0)!=operand1.charAt(0)){
			if(operand1.charAt(0)!=operand2.charAt(0)){
				remainder=integerSubtraction(remainder,divisor, length).substring(1);
			}
			else{
				remainder=integerAddition(remainder,divisor, length).substring(1);
			}
		}//��������뱻������ţ���������������ͬ�ţ������ӳ�������������������
		//�ж����
		String result=new String();
		if((operand1.charAt(0)==operand2.charAt(0)&&x==1)&&(operand1.charAt(0)!=operand2.charAt(0)&&x==0)){
			result="1"+quotient+remainder;
		}
		else{
			result="0"+quotient+remainder;
		}
		return result;
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		//����λ��չ
		String str1=operand1.substring(1);
		String str2=operand2.substring(1);
		String str=new String();
		while(str1.length()<length-1){
			str1="0"+str1;
		}
			str1=operand1.substring(0,1)+str1;
		while(str2.length()<length-1){
			str2="0"+str2;
		}
			str2=operand2.substring(0,1)+str2;
		//�жϷ���λ
			//ͬ��,�п������
		if(str1.charAt(0)==str2.charAt(0)){
			str=this.adder(str1.substring(1), str2.substring(1),'0', length).substring(1);
			char[] ch1=str1.toCharArray();
			char[] ch2=str2.toCharArray();
			double x=0;
			double y=0;
			for(int i=1;i<length;i++ ){
				x=Integer.parseInt(String.valueOf(ch1[i]))*Math.pow(2,length-1-i)+x;
				y=Integer.parseInt(String.valueOf(ch2[i]))*Math.pow(2,length-1-i)+y;
			}
			double num=x+y;
				if(num>=Math.pow(2,length)-1
						){
					str="1"+str1.charAt(0)+str;
				}
				else{
					str="0"+str1.charAt(0)+str;
				}
			}
		//��ţ����������
		if(str1.charAt(0)!=str2.charAt(0)){
			//������С������Ϊԭ��
			char[] ch1=str1.toCharArray();
			char[] ch2=str2.toCharArray();
			double x=0;
			double y=0;
			for(int i=1;i<length;i++ ){
				x=Integer.parseInt(String.valueOf(ch1[i]))*Math.pow(2,length-1-i)+x;
				y=Integer.parseInt(String.valueOf(ch2[i]))*Math.pow(2,length-1-i)+y;
			}
			double num=Math.max(x,y)-Math.min(x,y);
			String temp =new String();
			int num1=(int)num;
			while(num1!=0){
				int m = num1%2;
				num1 = num1/2;
				temp = m+temp;
			}
			while(temp.length()<length){
				temp = "0"+temp;
			}
			//�жϷ���λ
			if((str1.charAt(0)=='1'&&x>=y)||(str1.charAt(0)=='0'&&x<y)){
				str="0"+"1"+temp;
			}
			else{
				str="0"+"0"+temp;
			}
		}
		return str;
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	//дһ����������ԭ�뵽ʮ����
	public int  gettruevalue (String operand) {
		double x=0;
		char[] op=operand.toCharArray();
		for(int i=0;i<op.length;i++){
			x=Integer.parseInt(String.valueOf(op[i]))*Math.pow(2,op.length-i-1)+x;
		}
		return (int)x;
	}
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.

		String exponent1=operand1.substring(1,eLength+1);
		String fraction1=operand1.substring(eLength+1);
		String sign1=operand1.substring(0,1);
		String exponent2=operand2.substring(1,eLength+1);
		String fraction2=operand2.substring(eLength+1);
		String sign2=operand2.substring(0,1);
		String str=new String();
		String zero=new String();
		for(int j=0;j<=sLength+gLength;j++){
			zero="0"+zero;
		}
		//β��+����λ
		for(int i=0;i<gLength;i++){
			fraction1=fraction1+"0";
			fraction2=fraction2+"0";
		}
		fraction1="1"+fraction1;
		fraction2="1"+fraction2;
		//0�������ļ��
		if(floatTrueValue(operand1,sLength,eLength).equalsIgnoreCase("0")){
			str="0"+operand2;
		}
		else if(floatTrueValue(operand2,sLength,eLength).equalsIgnoreCase("0")){
			str="0"+operand1;
		}
		//ָ����ֵ
		int ev1=gettruevalue(exponent1);
		int ev2=gettruevalue(exponent2);
		int ev=ev1;//evΪ������ָ����С
		//�Ƚ�ָ����С����ɶ���
		if(ev1>ev2){
			fraction2=logRightShift(fraction2,ev1-ev2);
			//��С����ʧ
			if(fraction2.equals(zero)){
				str="0"+operand1;
			}
		}
		else if(ev1<ev2){
			fraction1=logRightShift(fraction1,ev2-ev1);
			ev=ev2;
			//��С����ʧ
			if(fraction1.equals(zero)){
				str="0"+operand2;
			}
		}
		//������β�����
		fraction1=sign1+fraction1;
		fraction2=sign2+fraction2;
		int k=0;
		while((4*k)<fraction1.length()){
			k++;
		}
		String fraction=signedAddition(fraction1, fraction2,4*k);//����Ϊ4*k+2
		String sign=fraction.substring(1,2);//��÷���λ

		fraction=fraction.substring(4*k-fraction1.length()+2);
		//�ж���Ч���Ƿ�����,����������Ч�����ƣ�ָ������һ
		boolean weishuyichu=false;
		double x=0;
		double y=0;
		char[] f1=fraction1.toCharArray();
		char[] f2=fraction2.toCharArray();
		for(int i=0;i<fraction1.length();i++){
			x=Integer.parseInt(String.valueOf(f1[i]))*Math.pow(2,f1.length-1-i)+x;
			y=Integer.parseInt(String.valueOf(f2[i]))*Math.pow(2,f2.length-1-i)+y;
		}
		if(x+y>Math.pow(2,sLength+gLength+1)-1){
			weishuyichu=true;
		}
			if(weishuyichu){
				fraction=logRightShift(fraction, 1);
				ev=ev+1;
			}
			
			boolean zhishuisyichu=false;
			//�ж�ָ���Ƿ�����
			if(ev>Math.pow(2,eLength-1)-1){
				zhishuisyichu=true;
			}
		//��񻯽����������Ч��������ָ��
			ev=ev+4*k-fraction1.length();
			fraction=fraction.substring(0,fraction.length()-4);
		while(fraction.charAt(0)=='0'){
			fraction=leftShift(fraction, 1);
			ev=ev-1;
		}
		fraction=leftShift(fraction, 1);
		ev=ev-1;
		fraction=fraction.substring(0,sLength);
		String exponent=new String();
		while(ev!=0){
			int m = ev%2;
			ev = ev/2;
			exponent = m+exponent;
		}
		while(exponent.length()<eLength){
			exponent="0"+exponent;
		}
		if(zhishuisyichu){
			str="1"+sign+exponent+fraction;
		}
		else{
			str="0"+sign+exponent+fraction;
		}
		return str;
	}
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		if(operand2.charAt(0)=='0'){
			operand2="1"+operand2.substring(1);
		}
		else{
			operand2="0"+operand2.substring(1);
		}
		String str=floatAddition(operand1, operand2, eLength, sLength, gLength);
		return str;
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String exponent1=operand1.substring(1,eLength+1);
		String fraction1=operand1.substring(eLength+1);
		String sign1=operand1.substring(0,1);
		String exponent2=operand2.substring(1,eLength+1);
		String fraction2=operand2.substring(eLength+1);
		String sign2=operand2.substring(0,1);
		String str=new String();
		boolean isyichu=false;
		String exponent=new String();
		String fraction=new String();
		//����Ƚ���
		String ex=new String();
		String fra=new String();
		for(int i=0;i<eLength;i++){
			ex="0"+ex;
		}
		for(int j=0;j<sLength;j++){
			fra="0"+fra;
		}
		//����0�����
		if((exponent1.equals(ex)&&fraction1.equals(fra))||(exponent2.equals(ex)&&fraction2.equals(fra))){
			for(int k=0;k<=eLength+sLength+1;k++){
				str="0"+str;
			}
		}
		//ָ����ֵ
		else{
		double exp=gettruevalue(exponent1)+gettruevalue(exponent2)-Math.pow(2,eLength-1)+1;
		
		if(exp>Math.pow(2,eLength-1)){
			isyichu=true;
		}
		
		//β����˲�����
		
		fraction1="01"+fraction1.substring(1);
		fraction2="01"+fraction2.substring(1);
		int k=0;
		while(4*k<2*fraction1.length()){
			k=k+1;
		}
		fraction=integerMultiplication(fraction1, fraction2,4*k);
		fraction=fraction.substring((4*k-2*fraction1.length()+1)*2);
		
		fraction=fraction.substring(0,sLength);
		//���
		if(exp<-Math.pow(2,eLength-1)+1){
			fraction=logRightShift(fraction,1);
			exp=exp+1;
		}
		int exp1=(int) exp;
		while(exp1!=0){
			exponent=(exp1%2)+exponent;
			exp1=exp1/2;
			
		}
		if(exponent.length()<eLength){
			exponent="0"+exponent;
		}
		exponent=exponent.substring(0,eLength);
		//����λ
		if(sign1.equals(sign2)){
			str="0"+exponent+fraction;
		}
		else{
			str="1"+exponent+fraction;
		}
		//�Ƿ����
		if(isyichu){
			str="1"+str;
		}
		else{
			str="0"+str;
		}
		}
			
			
		return str;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String exponent1=operand1.substring(1,eLength+1);
		String fraction1=operand1.substring(eLength+1);
		String sign1=operand1.substring(0,1);
		String exponent2=operand2.substring(1,eLength+1);
		String fraction2=operand2.substring(eLength+1);
		String sign2=operand2.substring(0,1);
		String str=new String();
		boolean isyichu=false;
		String exponent=new String();
		String fraction=new String();
		//����Ƚ���
				String ex=new String();
				String fra=new String();
				for(int i=0;i<eLength;i++){
					ex="0"+ex;
				}
				for(int j=0;j<sLength;j++){
					fra="0"+fra;
				}
		//���ǳ���Ϊ0
		if(exponent2.equals(ex)&&fraction2.equals(fra)){
			str="NaN";
		}
		//���Ǳ�����Ϊ0
		if(exponent1.equals(ex)&&fraction1.equals(fra)){
			for(int k=0;k<=eLength+sLength+1;k++){
				str="0"+str;
			}
		}
		//ָ����ֵ
		else{
		double exp=gettruevalue(exponent1)-gettruevalue(exponent2)+Math.pow(2,eLength-1)-1;
		
		if(exp>Math.pow(2,eLength-1)){
			isyichu=true;
		}
		//β�����������
		fraction1="1"+fraction1;
		fraction2="1"+fraction2;
		String newfra="";
		for(int i=0;i<fraction1.length();i++){
			newfra=newfra+"0";
		}
		
		String frac=fraction1;
		fraction=frac+newfra;
		int k=0;
		while(4*k<fraction1.length()){
			k=k+1;
		}
		String newfra1="";
		for(int j=0;j<fraction1.length();j++){
			frac=fraction.substring(0,fraction1.length());
			newfra1=fraction.substring(fraction1.length());
			 int x=gettruevalue(frac)-gettruevalue(fraction2);
			 
			 if(x>=0){
				 frac=integerRepresentation(String.valueOf(Math.abs(x)),fraction1.length()-1);
				 frac="0"+frac;
			 }
			 fraction=frac+newfra1;
			if(x>=0){
				fraction=fraction.substring(1)+"1";
			}
			else{
				fraction=fraction.substring(1)+"0";
			}
		}
		fraction=fraction.substring(fraction1.length()+1);
		System.out.println(fraction);
		//���
		if(exp<-Math.pow(2,eLength-1)+1){
			fraction=logRightShift(fraction,1);
			exp=exp+1;
		}
		int exp1=(int) exp;
		while(exp1!=0){
			exponent=(exp1%2)+exponent;
			exp1=exp1/2;
			
		}
		if(exponent.length()<eLength){
			exponent="0"+exponent;
		}
		exponent=exponent.substring(0,eLength);
		System.out.println(exponent);
		//����λ
		if(sign1.equals(sign2)){
			str="0"+exponent+fraction;
		}
		else{
			str="1"+exponent+fraction;
		}
		//�Ƿ����
		if(isyichu){
			str="1"+str;
		}
		else{
			str="0"+str;
		}
		}
		return str;
	}
}


package ru.geekbrains.java1.dz.dz3.gubenkoDM;


import java.util.Random;
import java.util.Scanner;

/**
 * Created by Nestor on 30.10.2016.
 */
public class CrossZeroMainClass {
    //размерность игрового поля
    private static final int FIELD_SIZE=5;
    //игровой символ игрока
    private static final char playerDot='X';
    //игровой символ компа
    private static final char pcDot='0';
    //символ пустоты на поле
    private static final char emptyDot='*';
    //символ оформления вывода
    private static final char printDotG='-';
    private static final char printDotV='|';
    //переменные для хранения координат предидущего хода игрока
    private static int prevX=1;
    private static int prevY=1;



    private static int xGlb;
    private static int yGlb;
    //массив игрового поля
    private static char[][] gameField=new char[FIELD_SIZE+1][FIELD_SIZE+1];
    //победная последовательность
    private static char[] winOrder=new char[FIELD_SIZE];
    //инициализация объекта сканера для считывания ввода с консоли
    private static Scanner sc=new Scanner(System.in);
    //иниц. объекта генер. случ. чисел
    //private static Random rand=new Random();

    public static void main(String[] args) {
       //начальная инициализация игрового поля
       initGameField();

       while(true){
           //вывод игрового поля
           printGameField();
           //ход игрока
           playerTurn();
           //проверка на победу игрока
           if (isWin(playerDot)){
               System.out.println("Поздравляем! Вы выиграли!");
               break;
           }
           //проверка на ничью
           if (isDraw()){
               System.out.println("Поздравляем! Ничья");
               break;
           }
           //ход ПК
           pcTurn();
           System.out.println("Поле после хода ПК!");
           //проверка на победу ПК
           if (isWin(pcDot)){
               printGameField();
               System.out.println("О господи! Вы проиграли!");
               break;
           }
           //проверка на ничью
           if(isDraw()){
               printGameField();
               System.out.println("Поздравляем! Ничья");
               break;
           }
       }

    }

    private static void initGameField(){
        for(int i=0,k=0,m=1;i<gameField.length;i++){
            for (int j=0;j<gameField.length;j++){
                if (i==0){
                    gameField[i][j]=Integer.toString(k).charAt(0); 
                    k++;
                }else {
                    if (j==0){
                        gameField[i][j]=Integer.toString(m).charAt(0);
                        m++;
                    }else {
                        gameField[i][j]=emptyDot;
                    }
                }
            }
        }
    }

    //вывод горизонталиных границ игрового поля
    private static void printBorder(){
        for(int i=0;i<FIELD_SIZE*3;i++){
            System.out.print(printDotG);
        }
        System.out.println();
    }

    private static void printGameField(){
       //вывод верхней границы
        printBorder();
        //вывод игрового поля
        for(int i=0;i<gameField.length;i++){
            System.out.print(printDotV);
            for(int j=0;j<gameField.length;j++){
                System.out.print(gameField[i][j]);
                System.out.print(printDotV);
            }
            System.out.println();
        }
        //System.out.println(Arrays.deepToString(gameField));
       //вывод нижней границы
        printBorder();
    }

    //метод установки фишки на игровое поле
    private static void setDot(int xCoord,int yCoord,char playerDotType){
        gameField[xCoord][yCoord]=playerDotType;
    }

    private static boolean isCellEmpty(int x,int y){
        return gameField[x][y]==emptyDot?true:false;
    }
    private static void playerTurn(){
        int x,y;
        while(true){
            System.out.println("Введите координаты расположения своей фишки в формате:"+" X от 1"+" до "+FIELD_SIZE+" пробел "+"Y от 1"+" до "+FIELD_SIZE);
            x=Integer.parseInt(sc.next());
            y=Integer.parseInt(sc.next());
            //проверим введенные значения на корректность
            if (((x>0)&&(x<=gameField.length-1))&&((y>0)&&(y<=gameField.length-1))){
               //проверим что поле не занято другой фишкой
               if (isCellEmpty(x,y)){
                    prevX=x;
                    prevY=y;
                    setDot(x,y,playerDot);
                    break;
                }else{
                   System.out.println("Поле по этим координатам уже заполнено!");
               }

            }else{
                System.out.println("Введены неверные координаты!");
            }
        }
    }

    private static boolean isInArr(int xCoord,int yCoord){
        return (((xCoord>0)&&(xCoord<=FIELD_SIZE))&&((yCoord>0)&&(yCoord<=FIELD_SIZE)));
    }




    private static boolean isGTest(int xCoord,int yCoord,int length){
        for(int i=0,k=0;i<length;i++){
            if ((xCoord<1)||(xCoord>gameField.length)){
                return false;
            }
            if (isInArr(xCoord,yCoord+k)){
                if (isCellEmpty(xCoord,yCoord+k)){
                    xGlb=xCoord;
                    yGlb=yCoord+k;
                    return true;
                }
            }
            k++;
        }
        return false;
    }
    private static boolean isVTest(int xCoord,int yCoord,int length){
        for(int i=0,k=0;i<length;i++){
            if ((yCoord<1)||(yCoord>gameField.length)){
                return false;
            }
            if (isInArr(xCoord+k,yCoord)){
                if (isCellEmpty(xCoord+k,yCoord)){
                    xGlb=xCoord+k;
                    yGlb=yCoord;
                    return true;
                }
            }
            k++;
        }
        return false;
    }




    private static void pcTurn(){
//        int x,y;
//        while(true){
//            x=rand.nextInt(FIELD_SIZE)+1;
//            y=rand.nextInt(FIELD_SIZE)+1;
//            if (isCellEmpty(x,y)){
//                setDot(x,y,pcDot);
//                break;
//            }
//        }
        while (true) {
            if (isGTest(prevX - 1, prevY - 1, 3)) {
                setDot(xGlb, yGlb, pcDot);
                break;
            } else if (isGTest(prevX + 1, prevY - 1, 3)) {
                setDot(xGlb, yGlb, pcDot);
                break;

            } else if (isVTest(prevX - 1, prevY - 1, 3)) {
                setDot(xGlb, yGlb, pcDot);
                break;
            } else if (isVTest(prevX - 1, prevY + 1, 3)) {
                setDot(xGlb, yGlb, pcDot);
                break;
            } else {
                for (int i = 1; i < gameField.length; i++) {
                    for (int j = 1; j < gameField.length; j++) {
                        if (isCellEmpty(i, j)) {
                            setDot(i, j, pcDot);
                            break;
                        }
                    }
                }
            }
        }

    }



    private static boolean isDraw(){
        for (char[] charArr :gameField) {
            for (char chArrElem:charArr) {
                if (chArrElem==emptyDot){
                    return false;
                }
            }
        }
        return true;
    }


    private static boolean isWinRange(char playerDotType){
        for (char iCh:winOrder) {
            if (iCh!=playerDotType){
                return false;
            }
        }
        return true;
    }


    private static boolean isWinG(char playerDotType){
        for(int i=1;i<gameField.length;i++){
            System.arraycopy(gameField[i],1,winOrder,0,FIELD_SIZE);
            if (isWinRange(playerDotType)){
                return true;
            }
        }
        return false;
    }

    private static boolean isWinV(char playerDotType){
        for(int i=1;i<gameField.length;i++){
            for(int j=1;j<gameField.length;j++){
                winOrder[j-1]=gameField[j][i];
            }
            if (isWinRange(playerDotType)){
                return true;
            }
        }
        return false;
    }

    private static boolean isWinD(char playerDotType){
        //проверка диагонали слева направо. главная диагональ
        for(int i=1;i<gameField.length;i++){
            for(int j=1;j<gameField.length;j++){
                if (i==j){
                    winOrder[i-1]=gameField[i][j];
                }
            }
        }

        if (isWinRange(playerDotType)){
            return true;
        }
        //проверка побочной диагонали
        for(int i=1;i<gameField.length;i++){
            for(int j=1;j<gameField.length;j++){
                if ((i+j)==gameField.length){
                    winOrder[i-1]=gameField[i][j];
                }
            }
        }
        if (isWinRange(playerDotType)){
            return true;
        }
        return false;
    }

    private static boolean isWin(char playerDotType){

        if ((isWinG(playerDotType))|(isWinV(playerDotType))|(isWinD(playerDotType))){
            return true;
        }
        return false;
    }

}

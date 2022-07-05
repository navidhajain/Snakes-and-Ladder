package com.example.snl;
import javafx.animation.*;
import javafx.scene.shape.*;
import javafx.scene.effect.Shadow;
import javafx.scene.effect.BlurType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    TranslateTransition translatearrow=new TranslateTransition();
    TranslateTransition translatetoken1=new TranslateTransition();
    TranslateTransition translatetoken2=new TranslateTransition();
    Shadow shadowboard2 = new Shadow();
    Shadow shadowboard3 = new Shadow();
    private final ArrayList<Block> gridforanimation = new ArrayList<>();
    private final ArrayList<Snake> snakelist=new ArrayList<>();
    private final ArrayList<Ladder> ladderlist=new ArrayList<>();
    private final Token token1 = new Token(1);
    private final Token token2 = new Token(2);
    private int diceno;
    private int p = 0;
    Dice di;

    @FXML
    private ImageView arrow;

    @FXML
    private ImageView board;

    @FXML
    private ImageView board2;

    @FXML
    private ImageView board3;

    @FXML
    private Button dice;

    @FXML
    private ImageView dice1;

    @FXML
    private ImageView dice2;

    @FXML
    private ImageView dice3;

    @FXML
    private ImageView dice4;

    @FXML
    private ImageView dice5;

    @FXML
    private ImageView dice6;

    @FXML
    private ImageView dicegif;

    @FXML
    private ImageView p1w;

    @FXML
    private ImageView p2w;

    @FXML
    private ImageView tempboard2;

    @FXML
    private ImageView tempboard3;

    @FXML
    private ImageView tok1;

    @FXML
    private ImageView tok2;

    void checkwhoseturn() {
        translatearrow.pause();
        p++;
        if(p%2==0) {
            tempboard2.setVisible(true);
            tempboard3.setVisible(false);
        }
        else {
            tempboard3.setVisible(true);
            tempboard2.setVisible(false);
        }
    }
    @FXML
    void dicerollin(ActionEvent event) {
        dicegif.setVisible(false);
        dice1.setVisible(false);
        dice2.setVisible(false);
        dice3.setVisible(false);
        dice4.setVisible(false);
        dice5.setVisible(false);
        dice6.setVisible(false);
        Dice dice = new Dice(dicegif, dice1, dice2, dice3, dice4, dice5, dice6);
        di = dice;
        dice.rolldice(translatearrow);
        diceno = dice.getDiceno();
        System.out.println(diceno);
        tokenmovement(p%2);
    }
    void tokenmovement(int playerturn) {
        if(playerturn==0) {
            if(token1.getLocked()) {
                if(diceno == 1) {
                    token1.setLocked(false);
                    moveup(translatetoken1, tok1);
                    token1.setPosn(0);
                }
            }
            else {
                if(99 - token1.getPosn() >= diceno) {
                    tokenmovement(translatetoken1, (double)gridforanimation.get(token1.getPosn() + diceno).getX(), (double)gridforanimation.get(token1.getPosn() + diceno).getY(), tok1);
                    System.out.println("posn: " + token1.getPosn());
                    token1.setPosn(token1.getPosn()+diceno);
                    System.out.println("posn: " + token1.getPosn());
                }
                if(token1.getPosn() == 99) {
                    checkfinish(1);
                }
                checkforsnake(token1,translatetoken1, tok1);
                checkforladder(token1, tok1);
            }
        }
        else {
            if(token2.getLocked()) {
                if(diceno == 1) {
                    token2.setLocked(false);
                    moveup(translatetoken2, tok2);
                    token2.setPosn(0);
                }
            }
            else {
                if(99 - token2.getPosn() >= diceno) {
                    tokenmovement(translatetoken2, (double)gridforanimation.get(token2.getPosn() + diceno).getX(), (double)gridforanimation.get(token2.getPosn() + diceno).getY(), tok2);
                    System.out.println("posn: " + token2.getPosn());
                    token2.setPosn(token2.getPosn()+diceno);
                    System.out.println("getPosn(): " + token2.getPosn());

                }
                if(token2.getPosn() == 99) {
                    checkfinish(2);
                }
                checkforsnake(token2, translatetoken2, tok2);
                checkforladder(token2, tok2);
            }
        }
        checkwhoseturn();
    }
    void tokenmovement(TranslateTransition translate, double x2, double y2, ImageView node){
        translate.setNode(node);
        translate.setDuration(Duration.millis(400));
        translate.setFromX(node.getTranslateX());
        translate.setFromY(node.getTranslateY());
        translate.setToX(x2);
        translate.setToY(y2);
        translate.play();
    }
    void moveup(TranslateTransition translate, ImageView i){
        translate.setNode(i);
        translate.setByY(-37);
        translate.setDuration(Duration.millis(400));
        translate.play();
    }

    void checkfinish(int finish) {
        if(finish == 1) {
            p1w.setVisible(true);
        }
        else {
            p2w.setVisible(true);
        }
        dice.setDisable(true);
    }

    void laddermove(double x1, double y1, double x2, double y2, ImageView node){
        System.out.println("inside ladder");
        TranslateTransition tr = new TranslateTransition();
        tr.setDelay(Duration.millis(600));
        tr.setNode(node);
        tr.setCycleCount(1);
        tr.setDuration(Duration.millis(400));
        tr.setByX(x2-x1);
        tr.setByY(y2-y1);
        tr.play();
        System.out.println("here");
        tokenmovement(p%2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shadowboard2.setBlurType(BlurType.GAUSSIAN);
        shadowboard3.setBlurType(BlurType.GAUSSIAN);
        tempboard2.setEffect(shadowboard2);
        tempboard3.setEffect(shadowboard3);
        translatearrow.setNode(arrow);
        translatetoken1.setNode(tok1);
        translatetoken2.setNode(tok2);
        translatearrow.setDuration(Duration.millis(400));
        translatearrow.setCycleCount(TranslateTransition.INDEFINITE);
        translatearrow.setByY(10);
        translatearrow.play();
        makegrid();
        makesnake();
        makeladder();
        p1w.setVisible(false);
        p2w.setVisible(false);
        tempboard3.setVisible(false);
        dicegif.setVisible(false);
        dice1.setVisible(true);
        dice2.setVisible(false);
        dice3.setVisible(false);
        dice4.setVisible(false);
        dice5.setVisible(false);
        dice6.setVisible(false);
    }


    void snakemove(int bodyLength, ArrayList<Block> body, ImageView ii) {
        PathTransition transition = new PathTransition();
        Path path = new Path();
        MoveTo moveTo = new MoveTo((double)body.get(0).getX(), (double)body.get(0).getY());
        path.getElements().add(moveTo);
        for (int i = 1; i < bodyLength; i++) {
            path.getElements().add(new LineTo((double)body.get(i).getX(), (double)body.get(i).getY()));
        }
        transition.setDuration(Duration.millis(1000));
        transition.setNode(ii);
        transition.setCycleCount(1);
        transition.setPath(path);
        transition.play();
    }

    void checkforladder(Token tok, ImageView i){
        if(tok.getPosn()==2){
            laddermove(ladderlist.get(0).getX1(), ladderlist.get(0).getY1(), ladderlist.get(0).getX2(), ladderlist.get(0).getY2(), i);
            tok.setPosn(23);
        }else if(tok.getPosn()==6){
            laddermove(ladderlist.get(1).getX1(), ladderlist.get(1).getY1(), ladderlist.get(1).getX2(), ladderlist.get(1).getY2(), i);
            tok.setPosn(33);
        }else if(tok.getPosn()==11){
            laddermove(ladderlist.get(2).getX1(), ladderlist.get(2).getY1(), ladderlist.get(2).getX2(), ladderlist.get(2).getY2(), i);
            tok.setPosn(30);
        }else if(tok.getPosn()==19){
            laddermove(ladderlist.get(3).getX1(), ladderlist.get(3).getY1(), ladderlist.get(3).getX2(), ladderlist.get(3).getY2(), i);
            tok.setPosn(40);
        }else if(tok.getPosn()==35){
            laddermove(ladderlist.get(4).getX1(), ladderlist.get(4).getY1(), ladderlist.get(4).getX2(), ladderlist.get(4).getY2(), i);
            tok.setPosn(45);
        }else if(tok.getPosn()==55){
            laddermove(ladderlist.get(5).getX1(), ladderlist.get(5).getY1(), ladderlist.get(5).getX2(), ladderlist.get(5).getY2(), i);
            tok.setPosn(62);
        }else if(tok.getPosn()==59){
            laddermove(ladderlist.get(6).getX1(), ladderlist.get(6).getY1(), ladderlist.get(6).getX2(), ladderlist.get(6).getY2(), i);
            tok.setPosn(80);
        }else if(tok.getPosn()==68){
            laddermove(ladderlist.get(7).getX1(), ladderlist.get(7).getY1(), ladderlist.get(7).getX2(), ladderlist.get(7).getY2(), i);
            tok.setPosn(92);
        }else if(tok.getPosn()==74){
            laddermove(ladderlist.get(8).getX1(), ladderlist.get(8).getY1(), ladderlist.get(8).getX2(), ladderlist.get(8).getY2(), i);
            tok.setPosn(94);
        }else if(tok.getPosn()==77){
            laddermove(ladderlist.get(9).getX1(), ladderlist.get(9).getY1(), ladderlist.get(9).getX2(), ladderlist.get(9).getY2(), i);
            tok.setPosn(96);
        }
    }
    void checkforsnake(Token tok, TranslateTransition translate, ImageView i){
        System.out.println("inside check for snake "+ tok.getPosn());
        if(tok.getPosn()==14){
            snakemove(snakelist.get(0).getbodylength(), snakelist.get(0).getsnakebody(), i);
            tok.setPosn(4);
        }else if(tok.getPosn()==21){
            snakemove(snakelist.get(1).getbodylength(), snakelist.get(1).getsnakebody(), i);
            tok.setPosn(1);
        }else if(tok.getPosn()==32){
            System.out.println("inside check for this snake "+ tok.getPosn());
            snakemove(snakelist.get(2).getbodylength(), snakelist.get(2).getsnakebody(), i);
            tok.setPosn(7);
        }else if(tok.getPosn()==43){
            snakemove(snakelist.get(3).getbodylength(), snakelist.get(3).getsnakebody(), i);
            tok.setPosn(22);
        }else if(tok.getPosn()==67){
            snakemove(snakelist.get(4).getbodylength(), snakelist.get(4).getsnakebody(), i);
            tok.setPosn(49);
        }else if(tok.getPosn()==78){
            snakemove(snakelist.get(5).getbodylength(),  snakelist.get(5).getsnakebody(), i);
            tok.setPosn(42);
        }else if(tok.getPosn()==84){
            snakemove(snakelist.get(6).getbodylength(),  snakelist.get(6).getsnakebody(), i);
            tok.setPosn(64);
        }else if(tok.getPosn()==91){
            snakemove(snakelist.get(7).getbodylength(),  snakelist.get(7).getsnakebody(), i);
            tok.setPosn(70);
        }else if(tok.getPosn()==93){
            snakemove(snakelist.get(8).getbodylength(),  snakelist.get(8).getsnakebody(), i);
            tok.setPosn(46);
        }else if(tok.getPosn()==97){
            snakemove(snakelist.get(9).getbodylength(),  snakelist.get(9).getsnakebody(), i);
            tok.setPosn(81);
        }
        //translate.stop();
    }
    void makesnake() {
        Snake s1=new Snake(); //15
        s1.makeBody(166, 321);
        s1.makeBody(129, 336);
        s1.makeBody(159, 357);
        s1.makeBody(136, 358); //5

        Snake s2=new Snake();//22
        s2.makeBody(46, 284);
        s2.makeBody(46, 288);
        s2.makeBody(39, 302);
        s2.makeBody(51, 334);//
        s2.makeBody(40, 349);//
        s2.makeBody(46, 358);//2

        Snake s3=new Snake();//33
        s3.makeBody(226, 247);
        s3.makeBody(231, 270);
        s3.makeBody(216, 297);
        s3.makeBody(228, 311);
        s3.makeBody(215, 333);
        s3.makeBody(226, 346);
        s3.makeBody(226, 358); //8

        Snake s4=new Snake();//44
        s4.makeBody(106, 210);
        s4.makeBody(78, 232);
        s4.makeBody(102, 261);
        s4.makeBody(78, 271);
        s4.makeBody(76, 284); //23

        Snake s5=new Snake();//68
        s5.makeBody(226, 136);
        s5.makeBody(258, 149);
        s5.makeBody(222, 178);
        s5.makeBody(270, 192);
        s5.makeBody(286, 210); //50

        Snake s6= new Snake(); //79
        s6.makeBody(46, 99);
        s6.makeBody(74, 115);
        s6.makeBody(48, 152);
        s6.makeBody(78, 182);
        s6.makeBody(59, 209);
        s6.makeBody(76, 210); //43

        Snake s7=new Snake(); //85
        s7.makeBody(136, 62);
        s7.makeBody(140, 78);
        s7.makeBody(129, 109);
        s7.makeBody(140, 128);
        s7.makeBody(136, 136);//65

        Snake s8=new Snake(); //92
        s8.makeBody(256, 25);
        s8.makeBody(284, 38);
        s8.makeBody(256, 73);
        s8.makeBody(286, 99); //71

        Snake s9=new Snake(); //94
        s9.makeBody(196, 25);
        s9.makeBody(188, 51);
        s9.makeBody(204, 73);
        s9.makeBody(186, 103);
        s9.makeBody(204, 134);
        s9.makeBody(187, 165);
        s9.makeBody(196, 210); //47

        Snake s10=new Snake();
        s10.makeBody(76, 25); //98
        s10.makeBody(49, 41);
        s10.makeBody(74, 61);
        s10.makeBody(46, 62); //82

        snakelist.add(s1);
        snakelist.add(s2);
        snakelist.add(s3);
        snakelist.add(s4);
        snakelist.add(s5);
        snakelist.add(s6);
        snakelist.add(s7);
        snakelist.add(s8);
        snakelist.add(s9);
        snakelist.add(s10);
    }
    void makeladder() {
        ladderlist.add(new Ladder(76, 358, 106, 284));
        ladderlist.add(new Ladder(196, 358, 196, 247));
        ladderlist.add(new Ladder(256, 321, 286, 247));
        ladderlist.add(new Ladder(16, 321, 16, 210));
        ladderlist.add(new Ladder(136, 247, 166, 210));
        ladderlist.add(new Ladder(136, 173, 76, 136));
        ladderlist.add(new Ladder(16, 173, 16, 62));
        ladderlist.add(new Ladder(256, 136, 226, 25));
        ladderlist.add(new Ladder(166, 99, 166, 25));
        ladderlist.add(new Ladder(76, 99, 106, 25));
    }
    void makegrid() {
        double startx=16, starty=-37, xdiff=28, ydiff=37;
        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx+xdiff;
        }

        startx=startx-xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx-xdiff;
        }

        startx=startx+xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx+xdiff;
        }

        startx=startx-xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx-xdiff;
        }

        startx=startx+xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx+xdiff;
        }

        startx=startx-xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx-xdiff;
        }

        startx=startx+xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx+xdiff;
        }

        startx=startx-xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx-xdiff;
        }

        startx=startx+xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx+xdiff;
        }

        startx=startx-xdiff;
        starty=starty-ydiff;

        for (double j = 0; j < 10; j++) {
            gridforanimation.add(new Block(startx, starty));
            startx=startx-xdiff;
        }
    }
}

class Token{
    private boolean isLocked=true;
    private int posn;

    Token(int temp) {
        if(temp == 1) {
            posn = -1;
        }
        else {
            posn = -1;
        }
    }

    public int getPosn() {
        return posn;
    }

    public void setPosn(int posn) {
        this.posn = posn;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    public boolean getLocked() {
        return isLocked;
    }
}

interface snl{
    int getstartidx();
    int getendidx();
    int getidx();
}

class Snake implements snl{
    private ArrayList<Block> snakeBody=new ArrayList<>();
    private int startidx, endidx, id;

    int getbodylength(){
        return snakeBody.size();
    }

    public ArrayList<Block> getsnakebody(){
        return snakeBody;
    }

    public void setSnakeBody(ArrayList<Block> snakeBody) {
        this.snakeBody = snakeBody;
    }

    public void setStartidx(int startidx) {
        this.startidx = startidx;
    }

    public void setEndidx(int endidx) {
        this.endidx = endidx;
    }

    public void setId(int id) {
        this.id = id;
    }

    void makeBody(double x, double y){
        snakeBody.add(new Block(x, y-395));
    }

    @Override
    public int getstartidx(){
        return startidx;
    }

    @Override
    public int getendidx(){
        return endidx;
    }

    @Override
    public int getidx(){
        return id;
    }
}

class Ladder implements snl{
    private double x1, y1, x2, y2;
    private int startidx, endidx, id;

    Ladder(double x11, double y11, double x22, double y22){
        x1=x11;
        x2=x22;
        y1=y11-395;
        y2=y22-395;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public void setStartidx(int startidx) {
        this.startidx = startidx;
    }

    public void setEndidx(int endidx) {
        this.endidx = endidx;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getidx(){
        return id;
    }

    @Override
    public int getstartidx(){
        return startidx;
    }

    @Override
    public int getendidx(){
        return endidx;
    }
}

class Block<t> {
    private t x, y;
    Block(t x, t y) {
        this.x = x;
        this.y = y;
    }

    public t getX() {
        return x;
    }

    public t getY() {
        return y;
    }
}
class Dice {
    Random random = new Random();
    private int diceno;
    private final ImageView dicegif;
    private final ImageView dice1;
    private final ImageView dice2;
    private final ImageView dice3;
    private final ImageView dice4;
    private final ImageView dice5;
    private final ImageView dice6;
    //TranslateTransition transArrow;
    public int getDiceno() {
        return diceno;
    }
    Dice(ImageView diceimg, ImageView diceimg1, ImageView diceimg2, ImageView diceimg3, ImageView diceimg4, ImageView diceimg5, ImageView diceimg6) {
        dicegif = diceimg;
        dice1 = diceimg1;
        dice2 = diceimg2;
        dice3 = diceimg3;
        dice4 = diceimg4;
        dice5 = diceimg5;
        dice6 = diceimg6;
        diceno = random.nextInt(6) + 1;
    }

    public void rolldice(TranslateTransition T) {
        diceno = random.nextInt(4)+1;
        dice1.setVisible(false);
        dicegif.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(EventHandler -> {
            T.play();
            dicegif.setVisible(false);
            if(diceno == 1) {
                dice1.setVisible(true);
            }
            else if(diceno == 2) {
                dice2.setVisible(true);
            }
            else if(diceno == 3) {
                dice3.setVisible(true);
            }
            else if(diceno == 4) {
                dice4.setVisible(true);
            }
            else if(diceno == 5) {
                dice5.setVisible(true);
            }
            else {
                dice6.setVisible(true);
            }

        });
        pause.play();
    }
}
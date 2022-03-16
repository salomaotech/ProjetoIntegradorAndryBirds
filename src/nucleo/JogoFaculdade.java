package nucleo;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/* Projeto Integrador da PUC primeiro semestre
 * Data de início 09/04/2020
 * Data de conclusão 
 * Autor Salomão Francisco da Silva
 * Curso ADS
 * Proposta jogo símples em Java
 */
public class JogoFaculdade extends JFrame {

    // fundo
    ImageIcon FundoCena1 = new ImageIcon(getClass().getResource("fundo.gif"));

    // personagens
    ImageIcon Personagem1 = new ImageIcon(getClass().getResource("personagem1.png"));

    // npcs
    ImageIcon Obstaculo1 = new ImageIcon(getClass().getResource("obstaculo1.png"));
    ImageIcon Obstaculo2 = new ImageIcon(getClass().getResource("obstaculo2.png"));
    ImageIcon Obstaculo3 = new ImageIcon(getClass().getResource("obstaculo3.png"));

    // labels
    JLabel LabelCena1 = new JLabel(FundoCena1);
    JLabel LabelPersonagem1 = new JLabel(Personagem1);
    JLabel LabelObstaculo1 = new JLabel(Obstaculo1);
    JLabel LabelObstaculo2 = new JLabel(Obstaculo2);
    JLabel LabelObstaculo3 = new JLabel(Obstaculo3);
    JLabel LabelObstaculo4 = new JLabel(Obstaculo3);

    // audio clip
    AudioClip audio;

    // coordenadas iniciais do meu personagem 1
    int px = 10;
    int py = 380;

    // coordenadas dos obstaculos
    int obsx1 = 350;
    int obsy1 = 240;
    int obsx2 = 300;
    int obsy2 = -160;
    int obsx3 = 130;
    int obsy3 = 170;
    int obsx4 = 380;
    int obsy4 = 170;

    // contador de obstaculo
    int contador_obstaculo3 = 0;
    int incrementador = 1;

    // relacionado ao temporizador
    Timer Temporizador = new Timer();
    int velocidade_tempo = 1200;

    // método principal
    public static void main(String[] args) {

        new JogoFaculdade();

    }

    // roda um som
    public void Som(String arquivo, boolean loop) {

        URL url = getClass().getResource(arquivo + ".wav");
        audio = Applet.newAudioClip(url);
        audio.play();

        if (loop == true) {

            audio.loop();

        }

    }

    // esta tarefa serve para dar um pouco mais de vida ao jogo :-)
    TimerTask Tarefa = new TimerTask() {

        // aqui eu coloco a vida do jogo
        public void run() {

            // colisão
            Colisao();

            // atualiza coordenadas
            obsx1 -= 40;
            obsx2 -= 40;

            // valida coordenada
            if (obsx1 < -52) {

                obsx1 = 300;

            }

            // valida coordenada
            if (obsx2 < -52) {

                obsx2 = 300;

            }

            // seta novas posições
            LabelObstaculo1.setBounds(obsx1, obsy1, 52, 320);
            LabelObstaculo2.setBounds(obsx2, obsy2, 52, 320);

            // valida contador de obstaculo
            if (contador_obstaculo3 >= 1) {

                contador_obstaculo3 = 0;
                obsx3 -= 30 + incrementador;
                obsx4 += 30 + incrementador;
                LabelObstaculo3.setBounds(obsx3, obsy3, 60, 60);
                LabelObstaculo4.setBounds(obsx4, obsy4, 60, 60);

                if (obsx3 <= -120) {
                    obsx3 = 320;
                }

                if (obsx4 >= 320) {
                    obsx4 = -120;
                }

            }

            if (incrementador >= 60) {
                incrementador = 0;
            } else {
                incrementador += 6;
            }

            // atualiza o contador de obstaculo
            contador_obstaculo3++;

        }

    };

    // inicializador
    public JogoFaculdade() {

        // nova janela JFrame
        setTitle("Integrador 1 - PUC");
        setSize(320, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setResizable(false);
        setVisible(true);
        add(LabelObstaculo1);
        add(LabelObstaculo2);
        add(LabelPersonagem1);
        add(LabelObstaculo3);
        add(LabelObstaculo4);
        add(LabelCena1);

        // propriedades iniciais dos objetos
        EditarComponentes();

        // relacionado ao teclado e mouse
        Movimento();

        // novo timer
        Temporizador.scheduleAtFixedRate(Tarefa, velocidade_tempo, velocidade_tempo);

        // som
        Som("angry", true);

    }

    public void EditarComponentes() {

        LabelCena1.setBounds(0, 0, 320, 450);
        LabelPersonagem1.setBounds(px, py, 50, 50);
        LabelObstaculo1.setBounds(obsx1, obsy1, 52, 320);
        LabelObstaculo2.setBounds(obsx2, obsy2, 52, 320);
        LabelObstaculo3.setBounds(130, 170, 60, 60);
        LabelObstaculo4.setBounds(130, 170, 60, 60);

    }

    public void Movimento() {

        addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == 38) {

                    py -= 15;

                }

                if (e.getKeyCode() == 40) {

                    py += 15;

                }

                if (e.getKeyCode() == 37) {

                    px -= 15;

                }

                if (e.getKeyCode() == 39) {

                    px += 15;

                }

                if (py > 380) {

                    py = 380;

                }

                if (py < 15) {

                    py = 15;

                }

                if (px < 10) {

                    px = 10;
                }

                if (px > 260) {

                    px = 260;

                }

                // move meu personagem
                LabelPersonagem1.setBounds(px, py, 50, 50);

                // trata uma colisão
                Colisao();

            }

            public void keyReleased(KeyEvent e) {

            }
        ;

    }

    );

	}

	// trata uma colisão
	public void Colisao() {

        String mensagem = new String("Game Over!");

        if (bateu(LabelPersonagem1, LabelObstaculo1) == true || bateu(LabelPersonagem1, LabelObstaculo2) == true || bateu(LabelPersonagem1, LabelObstaculo3) == true || bateu(LabelPersonagem1, LabelObstaculo4) == true) {

            // para o som
            audio.stop();

            // som
            Som("morreu", false);

            // para a tarefa atual
            Tarefa.cancel();

            // exibe a mensagem de fim
            JOptionPane.showMessageDialog(null, mensagem);

            // cria uma nova instancia desta janela
            new JogoFaculdade();

            // oculta
            setVisible(false);

            // remove da memoria
            dispose();

        };

    }

    // identifica a colisão entre dois objetos
    public boolean bateu(Component a, Component b) {

        int aX = a.getX();
        int aY = a.getY();
        int ladoDireitoA = aX + a.getWidth();
        int ladoEsquerdoA = aX;
        int ladoBaixoA = aY + a.getHeight();
        int ladoCimaA = aY;

        int bX = b.getX();
        int bY = b.getY();
        int ladoDireitoB = bX + b.getWidth();
        int ladoEsquerdoB = bX;
        int ladoBaixoB = bY + b.getHeight();
        int ladoCimaB = bY;

        boolean bateu = false;

        boolean cDireita = false;
        boolean cCima = false;
        boolean cBaixo = false;
        boolean cEsquerda = false;

        if (ladoDireitoA >= ladoEsquerdoB) {
            cDireita = true;
        }
        if (ladoCimaA <= ladoBaixoB) {
            cCima = true;
        }
        if (ladoBaixoA >= ladoCimaB) {
            cBaixo = true;
        }
        if (ladoEsquerdoA <= ladoDireitoB) {
            cEsquerda = true;
        }

        if (cDireita && cEsquerda && cBaixo && cCima) {
            bateu = true;
        }

        return bateu;
    }

}

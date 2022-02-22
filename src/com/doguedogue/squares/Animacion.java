package com.doguedogue.squares;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;

/**
 * @author doguedogue 
 * created on 25/AGO/2005
 */

public class Animacion extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	private double longitud = 200;
	private double posx = 50;
	private double posy = 50;
	private int quantity = 1;
	private float fraccion = 0.1f;
	private Thread animacionThread = null;
	private int coloractual = 0;
	private int numcuadros = 50;
	private boolean modifFraccion = true;
	private int tiempo = 100;
	
	private Object[] colors = new Object[] { Color.orange, Color.black, Color.white, Color.cyan, Color.gray,
			Color.green, Color.lightGray, Color.magenta, Color.pink, Color.red, Color.yellow };

	public Animacion(double longitud, int numcuadros, int tiempo, float fraccion) {
		this.longitud = longitud;
		this.numcuadros = numcuadros;
		this.tiempo = tiempo; 
		this.fraccion = fraccion;

		
		if (animacionThread == null) {
			animacionThread = new Thread(this, "Animacion");
			animacionThread.start();
		}
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Animacion() {
		if (animacionThread == null) {
			animacionThread = new Thread(this, "Animacion");
			animacionThread.start();
		}
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		if (animacionThread == null) {
			animacionThread = new Thread(this, "Animacion");
			animacionThread.start();
		}
	}

	public void run() {
		Thread myThread = Thread.currentThread();
		while (animacionThread == myThread) {
			if (quantity == numcuadros) { // Termino un ciclo de cuadros
				coloractual++;
				if (coloractual > (colors.length - 1)) { // Termino un ciclo de colores
					coloractual = 0;
				}
				if (modifFraccion)
					fraccion = 1f-fraccion;
				else
					fraccion = 1f-fraccion;
				modifFraccion = !modifFraccion;
				setQuantity(1);
			} else
				quantity++;
			repaint();
			try {
				Thread.sleep(tiempo); // Milisegundos
			} catch (InterruptedException e) {

			}
		}
	}

	// overrides Applet's stop method, not Thread's
	public void stop() {
		animacionThread = null;
	}

	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		setBackground(Color.darkGray);
		setForeground((Color) colors[coloractual]);
		Graphics2D g = (Graphics2D) g2;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// posicion
		posicionLongitud();
		// Cuadricula
		cuadricula(g);
	}

	private void posicionLongitud() {
		posx = (getWidth() - longitud) / 2;
		posy = (getHeight() - longitud) / 2;

	}

	private void cuadricula(Graphics2D g2) {
		Point2D.Double Aini = new Point2D.Double(posx, posy);
		Point2D.Double Bini = new Point2D.Double(posx + longitud, posy);
		Point2D.Double Cini = new Point2D.Double(posx, posy + longitud);
		Point2D.Double Dini = new Point2D.Double(posx + longitud, posy + longitud);
		for (int i = 0; i < quantity; i++) {
			g2.draw(new Line2D.Double(Aini, Bini)); // AB
			g2.draw(new Line2D.Double(Bini, Dini)); // BD
			g2.draw(new Line2D.Double(Dini, Cini)); // DC
			g2.draw(new Line2D.Double(Cini, Aini)); // CA
			// A'
			Point2D.Double Ainicial = getNewPosition(Cini, Aini);
			// B'
			Point2D.Double Binicial = getNewPosition(Aini, Bini);
			// C'
			Point2D.Double Cinicial = getNewPosition(Dini, Cini);
			// D'
			Point2D.Double Dinicial = getNewPosition(Bini, Dini);
			Aini = Ainicial;
			Bini = Binicial;
			Cini = Cinicial;
			Dini = Dinicial;
		}

	}

	private Point2D.Double getNewPosition(Point2D.Double t, Point2D.Double d) {
		double y = t.getY() + (fraccion) * (d.getY() - t.getY());
		double x = t.getX() + (fraccion) * (d.getX() - t.getX());
		return new Point2D.Double(x, y);
	}

	public void setQuantity(int quanti) {
		quantity = quanti;
		repaint();
	}

	public void setFraccion(float fracc) {
		fraccion = fracc;
		repaint();
	}

	public int getQuantity() {
		return quantity;
	}

	public float getFraccion() {
		return fraccion;
	}

	private void jbInit() throws Exception {
		this.setBackground(Color.darkGray);
	}

}
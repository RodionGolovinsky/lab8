package controllers.map;

import classesandenums.Person;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utility.CommandManager;

import java.util.*;

public class MapController {

    private final Canvas canvas;
    private final double WIDTH;
    private final double HEIGHT;
    private final LinkedHashSet<MapPerson> persons = new LinkedHashSet<>();
    private final HashMap<String, Color> colors = new HashMap<>();
    private GraphicsContext gc;
    private double factor;
    private double maxSize;
    private AnimationTimer loop;
    private final CommandManager commandManager;

    public MapController(GraphicsContext gc, Canvas canvas, LinkedHashSet<Person> set, CommandManager commandManager) {
        this.gc = gc;
        this.canvas = canvas;
        this.commandManager = commandManager;
        WIDTH = canvas.getWidth();
        HEIGHT = canvas.getHeight();
        for (Person p : set) {
            colors.put(p.getOwner().getUsername(), new Color(Math.random(), Math.random(), Math.random(), 1));
        }
        set.forEach(p -> persons.add(new MapPerson(p)));
        drawMap();
        persons.forEach(p -> {
            setCoordinates(p);
            drawPerson(p, colors.get(p.getPerson().getOwner().getUsername()));
        });
    }

    public void startDraw(LinkedHashSet<Person> set) {
        maxSize = set.stream()
                .mapToDouble(Person::getHeight)
                .max().orElse(HEIGHT);
        if (set.size() > persons.size()) {
            LinkedHashSet<MapPerson> old = new LinkedHashSet<>(persons);
            persons.clear();
            set.forEach(p -> persons.add(new MapPerson(p)));
            drawMap();
            for (MapPerson p : persons) {
                setCoordinates(p);
                if (!colors.containsKey(p.getPerson().getOwner().getUsername())) {
                    colors.put(p.getPerson().getOwner().getUsername(), new Color(Math.random(), Math.random(), Math.random(), 1));
                }
                drawPerson(p, colors.get(p.getPerson().getOwner().getUsername()));
                if (!old.contains(p)) {
                    animation(p, colors.get(p.getPerson().getOwner().getUsername()));
                }
            }
        } else {
            persons.clear();
            set.forEach(p -> persons.add(new MapPerson(p)));
            drawMap();
            persons.forEach(p -> {
                setCoordinates(p);
                drawPerson(p, colors.get(p.getPerson().getOwner().getUsername()));
            });
        }
    }

    private void drawMap() {
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        double maxX = persons.stream().mapToDouble(p -> p.getPerson().getCoordinates().getX()).max().orElse(WIDTH);
        double minX = persons.stream().mapToDouble(p -> p.getPerson().getCoordinates().getX()).min().orElse(HEIGHT);
        double maxY = persons.stream().mapToDouble(p -> p.getPerson().getCoordinates().getY()).max().orElse(HEIGHT);
        double minY = persons.stream().mapToDouble(p -> p.getPerson().getCoordinates().getY()).min().orElse(HEIGHT);
        factor = 2 * Math.max(maxX, Math.max(-Math.min(minX, minY), maxY));
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.strokeLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
        gc.strokeLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
        gc.fillText("0.0", WIDTH / 2, HEIGHT / 2 + 20);
        gc.fillText(String.valueOf((int) (-factor * 2 / 2.2 / 4)), WIDTH / 4, HEIGHT / 2 + 20);
        gc.fillText(String.valueOf((int) (factor * 2 / 2.2 / 4)), WIDTH * 3.0 / 4.0, HEIGHT / 2 + 20);
    }

    private void setSize(MapPerson p) {
        if (p.getPerson().getHeight() > maxSize / 2) {
            p.setSize(HEIGHT / 4);
        } else if (p.getPerson().getHeight() > maxSize / 4) {
            p.setSize(HEIGHT / 6);
        } else {
            p.setSize(HEIGHT / 8);
        }
    }

    private void setCoordinates(MapPerson p) {
        setSize(p);
        p.setX((p.getPerson().getCoordinates().getX() + factor / 2.0) * (WIDTH / factor));
        p.setY((p.getPerson().getCoordinates().getY() + factor / 2.0) * (HEIGHT / factor));
        p.setY(p.getY() + HEIGHT - 2 * p.getY());
    }

    private void drawPerson(MapPerson p, Color color) {
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(p.getSize() / 20);
        double x = p.getX();
        double y = p.getY();
        gc.strokeLine(x, y - p.getSize() / 4, x, y + p.getSize() / 4);
        gc.strokeLine(x - p.getSize() / 4, y, x + p.getSize() / 4, y);
        gc.strokeLine(x, y + p.getSize() / 4, x + p.getSize() / 4, y + p.getSize() / 2);
        gc.strokeLine(x, y + p.getSize() / 4, x - p.getSize() / 4, y + p.getSize() / 2);
        gc.fillOval(x - p.getSize() / 8, y - p.getSize() / 2, p.getSize() / 4, p.getSize() / 4);
    }

    private void drawGreeting(MapPerson p) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(p.getSize() / 27);
        double x = p.getX();
        double y = p.getY() - p.getSize();
        gc.strokeOval(x, y, p.getSize(), p.getSize() / 2);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(p.getSize() / 7));
        gc.fillText(commandManager.getLocaleManager().localize("greeting"), x + p.getSize() / 5, y + p.getSize() / 3.5);
    }

    private void animation(MapPerson p, Color color) {
        loop = new AnimationTimer() {
            final double startX = p.getX() + p.getSize() / 4;
            final double startY = p.getY();
            final double speedX = 1;
            final double speedY = 2;
            double y = startY;
            double x = startX;
            boolean direct = true;
            int cnt = 0;

            @Override
            public void handle(long now) {
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(p.getSize() / 18);
                gc.strokeLine(p.getX(), p.getY(), x + 1, y);
                if (y <= startY - p.getSize() / 4) {
                    direct = false;
                }
                if (direct) {
                    gc.setStroke(color);
                    gc.setLineWidth(p.getSize() / 20);
                    gc.strokeLine(p.getX(), p.getY(), x - speedX, y - speedY);
                    x -= speedX;
                    y -= speedY;
                } else {
                    gc.setStroke(color);
                    gc.setLineWidth(p.getSize() / 20);
                    gc.strokeLine(p.getX(), p.getY(), x + speedX, y + speedY);
                    x += speedX;
                    y += speedY;
                }
                if (x == startX) {
                    direct = true;
                }
                cnt++;
                if (cnt == 68) {
                    loop.stop();
                    drawGreeting(p);
                }
            }
        };
        loop.start();
    }

    public Person getClicked(double x, double y) {
        for (MapPerson p : persons) {
            if (x >= p.getX() - p.getSize() / 4 && x <= p.getX() + p.getSize() / 4
                    && y >= p.getY() - p.getSize() / 2 && y <= p.getY() + p.getSize() / 2) {
                return p.getPerson();
            }
        }
        return null;
    }

}

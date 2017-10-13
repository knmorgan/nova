package nova.game.engine.particle;

import java.awt.Graphics;

import nova.game.util.LinkList;

/**
 * Class is responsible for maintaining all the particles in the game.
 *
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 */
public class ParticleEngine
{
    public static ParticleEngine instance = new ParticleEngine();

    private LinkList<Particle> particles;

    /**
     * Initializes the list of particles.
     */
    private ParticleEngine()
    {
        particles = new LinkList<Particle>();
    }

    /**
     * Calls act on all particles in the engine, and also
     * removes particles if need be.
     */
    public void act()
    {
        particles.startOver();
        while(particles.hasNext())
        {
            Particle p = particles.next();
            p.act();
            if(p.isDone())
            {
                particles.remove();
            }
        }
    }

    /**
     * Adds a particle to the engine.
     *
     * @param p The particle to be added.
     */
    public void addParticle(Particle p)
    {
        particles.add(p);
    }

    /**
     * Creates 100 particles at a specified location.
     *
     * @param x The x-coordinate of the burst of particles
     * @param y The y-coordinate of the burst of particles
     */
    public void createParticles(double x, double y)
    {
        for(int i=0; i<100; i++)
        {
            particles.add(new PointParticle(x, y));
        }
    }

    /**
     * Creates 10 line particles at the specificed location
     * @param x The x-coordinate of the burst of particles
     * @param y The y-coordinate of the burst of particles
     */
    public void createLineParticles(double x, double y)
    {
        for(int i=0; i<10; i++)
        {
            particles.add(new LineParticle(x-3, y, x+3, y, .04));
        }
    }

    /**
     * Returns the number of particles currently in the engine.
     *
     * @return Number of particles
     */
    public int numParticles()
    {
        return particles.size();
    }

    /**
     * Paints all particles in the engine.
     *
     * @param g The Graphics object being drawn to
     */
    public void paint(Graphics g)
    {
        particles.startOver();
        while(particles.hasNext())
        {
            particles.next().paint(g);
        }
    }
}

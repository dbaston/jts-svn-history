package test.jts.perf.triangulate;

import java.util.*;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.util.*;
import com.vividsolutions.jts.triangulate.*;

/**
 * Test robustness of Delaunay computation.
 * Test dataset is constructed to have many points
 * with a large base offset.  This reduces 
 * the precision available for the arithmetic in the inCircle test.
 * This causes incorrect values to be computed by using
 * the simple double-precision approach.
 * 
 * @author Martin Davis
 *
 */
public class DelaunayRobustTest 
{
  public static void main(String args[]) {
  	DelaunayRobustTest test = new DelaunayRobustTest();
  	test.run();
  }
  
	public void run()
	{
		run(1000000);
	}
	
	final static GeometryFactory geomFact = new GeometryFactory();
	
  final static double SIDE_LEN = 1.0;
  final static double BASE_OFFSET = 0; //1.0e6;
	
	public void run(int nPts)
	{
    System.out.println("Base offset: " + BASE_OFFSET);
    
    
		List pts = randomPointsInGrid(nPts, BASE_OFFSET, BASE_OFFSET);
		System.out.println("# pts: " + pts.size());
		Stopwatch sw = new Stopwatch();
		DelaunayTriangulationBuilder builder = new DelaunayTriangulationBuilder();
		builder.setSites(pts);
		
//		Geometry g = builder.getEdges(geomFact);
		// don't actually form output geometry, to save time and memory
		builder.getSubdivision();
		
		System.out.println("  --  Time: " + sw.getTimeString()
				+ "  Mem: " + Memory.usedTotalString());
//		System.out.println(g);
	}
	
	List randomPointsInGrid(int nPts, double basex, double basey)
	{
		List pts = new ArrayList();
		
		int nSide = (int) Math.sqrt(nPts) + 1;
		
		for (int i = 0; i < nSide; i++) {
			for (int j = 0; j < nSide; j++) {
				double x = basex + i * SIDE_LEN + SIDE_LEN * Math.random();
				double y = basey + j * SIDE_LEN + SIDE_LEN * Math.random();
				pts.add(new Coordinate(x, y));
			}
		}
		return pts;
	}
	
	List randomPoints(int nPts)
	{
		List pts = new ArrayList();
		
		for (int i = 0; i < nPts; i++) {
				double x = SIDE_LEN * Math.random();
				double y = SIDE_LEN * Math.random();
				pts.add(new Coordinate(x, y));
		}
		return pts;
	}
}

import java.lang.Math; // header stuff MUST go above the first class

//
//  ### All length, width and height are in inches.   ###
//
// USPS girth formula: 2*w + 2*h; 
// If a package's length + girth (l + 2*w + 2*h) is more than 108 inches
// that package CANNOT be shipped by USPS
// For more info see http://pe.usps.com/text/qsg300/Q201e.htm

// FedEx Dimensional weight calculator:
// Dimensional Weight (L x W x H / 139) [units: in/lb]
// IF the dimensional weight exceeds the actual weight
// THEN the dimensional weight (heavier weight) applies
// For more info see http://www.fedex.com/in/tools/dimweight.html

public class Package
{
  private double l;
  private double w;
  private double h;
  private double aw;			// actual weight of the package
  public Package(double length, double width, double height)  // constructor
  {
    l = length;
    w = width;
    h = height;
  }

  public Package(double length, double width, double height, double actualWeight)  // another constructor
  {
    l = length;
    w = width;
    h = height;
    aw = actualWeight;			// actual weight of the package
  }
    
  public double getL() { return l; }
  public double getW() { return w; }
  public double getH() { return h; }
  public double getActualWeight() {return aw; }
  
  public String toString()
  {
    return "Package's length: " + l + ", width:  " + w + ", height: " + h;
  }
}

public class FedEx
{
  private double dm;	// Dimensional weight
  public double DimensionalWeight(Package p)
  {
    dm = p.getL() * p.getW() * p.getH() / 139.0;
    dm = Math.round(dm);
    return dm;
  }
  
  public double PrevailingWeight(Package p)
  {
    dm = DimensionalWeight(p);
    
    if (p.getActualWeight() <= 0)
    {
      System.out.println("## Error: actual weight cannot be zero.");
      return -1.0;
    }
      
    else if (dm > p.getActualWeight())
    {
      return dm;
    }
    else
    {
      return p.getActualWeight();
    }
  }  
}

public class USPS
{
  private double g;    // Girth
  private double lg;   // Length plus Girth
  private boolean canBeShipped;
  
  public double Girth (Package p)
  {
    g = 2*p.getW() + 2*p.getH();
    return g;
  }
    
  public double LengthplusGirth(Package p)
  {
    g = Girth(p);
    lg = p.getL()+g;
    return lg;
  }
    
  public boolean CanShippedbyUSPS(Package p)
  {
    lg = LengthplusGirth (p);
    
    if (lg <= 108)
      return true;
    else
      return false;        
  }    
}  

// Main class: DimensionalWeight
public class DimensionalWeight
{
  public static void main(String[] args)
  {    
    USPS usps = new USPS();
        
    Package p3 = new Package(10.0, 8.0, 5.0);
    System.out.println(p3.toString());
    System.out.println("Package's girth: " + usps.Girth(p3));
    System.out.println("Package's length plus girth: " + usps.LengthplusGirth(p3));
    System.out.println("Can this package be shipped by USPS? : " + usps.CanShippedbyUSPS(p3));
    
    System.out.println();
    
    Package p4 = new Package(101.0, 81.0, 51.0);
    System.out.println(p4.toString());
    System.out.println("Package's girth: " + usps.Girth(p4));
    System.out.println("Package's length plus girth: " + usps.LengthplusGirth(p4));    
    System.out.println("Can this package be shipped by USPS? : " + usps.CanShippedbyUSPS(p4));
    
    System.out.println();
    
    FedEx fedex = new FedEx();
    
    Package p5 = new Package(24.0, 18.0, 10.0, 5.0);
    System.out.println(p5.toString());    
    System.out.println("FedEx dimensional weight: " + fedex.DimensionalWeight(p5));
    System.out.println("Package's actual weight: " + p5.getActualWeight());
    System.out.println("FedEx prevailing shipping weight: " + fedex.PrevailingWeight(p5));
    
    System.out.println();

    Package p6 = new Package(24.0, 18.0, 10.0, 33.0);
    System.out.println(p6.toString());    
    System.out.println("FedEx dimensional weight: " + fedex.DimensionalWeight(p6));
    System.out.println("Package's actual weight: " + p6.getActualWeight());
    System.out.println("FedEx prevailing shipping weight: " + fedex.PrevailingWeight(p6));

    System.out.println();
    
    Package p7 = new Package(24.0, 18.0, 10.0, 0);
    System.out.println(p7.toString());    
    System.out.println("FedEx dimensional weight: " + fedex.DimensionalWeight(p7));
    System.out.println("Package's actual weight: " + p7.getActualWeight());
    System.out.println("FedEx prevailing shipping weight: " + fedex.PrevailingWeight(p7));
        
  }
}

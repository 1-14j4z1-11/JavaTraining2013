package ex16_02;

interface IFace1 { }
interface IFace2 { }

class Base { }
class Derived extends Base { }

public class Sample
{
	static public class Inner extends Derived implements IFace1, IFace2
	{ }
}

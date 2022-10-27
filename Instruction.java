

public class Instruction
{
    String action;
    Expression expression;
    Program program;
    Condition condition;

    public Instruction(String action, Expression expression)
    {
        this.action = action;
        this.expression = expression;
    }
    public Instruction(String action,Program p,Condition c)
    {
        this.action = action;
        this.expression = expression;
        this.program=p;
        this.condition=c;
    }
}

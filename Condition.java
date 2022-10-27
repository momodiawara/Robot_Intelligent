import java.util.LinkedList;

public class Condition {
    Expression expression1,expression2;
    Condition condition1,condition2;
    String c;
    public Condition(Expression e1,String c,Expression e2){
        this.expression1=e1;
        this.c=c;
        this.expression2=e2;
        this.condition1=null;
        this.condition2=null;

    }
    public Condition(Condition c1,String c,Condition c2){
        this.expression1=null;
        this.c=c;
        this.expression2=null;
        this.condition1=c1;
        this.condition2=c2;

    }

    public Expression getExpression1() {
        return expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public Condition getCondition1() {
        return condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

    public String getC() {
        return c;
    }


}

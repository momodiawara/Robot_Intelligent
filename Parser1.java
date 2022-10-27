import java.util.LinkedList;
import java.io.Reader;

public class Parser1 implements Parser {


    protected LookAhead1 reader;

    public Parser1(LookAhead1 reader) {
        this.reader = reader;
    }



    @Override
    public Program parseProgram(String exeName, Reader lookAhead1) {
     Program program=null;
        try {
            program=nontermProgram();
            reader.eat(Sym.EOF);

        } catch (Exception e) {
            e.printStackTrace();
        }
     return program;
    }

    public Program  nontermProgram() throws Exception{
        Program program,progRetourner=null;

        if(reader.check(Sym.AVANCER) || reader.check(Sym.TOURNER) || reader.check(Sym.ECRIRE) ) {
        Instruction instruction=NontermInstr();
        reader.eat(Sym.SEMICOL);
        program= nontermProgram();
        progRetourner=new Program(instruction,program);

        }
   if(reader.check(Sym.EOF)){
       reader.eat(Sym.EOF);
   }
    else{
       throw new Exception();
    }
    return progRetourner;

    }

    public Expression NontermExpr() throws Exception{
        Expression expression=null;

        if(reader.check(Sym.LIRE)){
        reader.eat(Sym.LIRE);
        expression=new Expression("Lire");
    }
    else if(reader.check(Sym.INT)){
        int n=termInt();
        expression=new Expression(n);
    }
     else if(reader.check(Sym.LPAR)){
        reader.eat(Sym.LPAR);

        Expression expression1=NontermExpr();

        if(reader.check(Sym.INT))

        {
            Expression expression2=NontermExpr();
            reader.eat(Sym.RPAR);

            if(expression1.expressions.size()==1 && !expression1.expressions.get(0).equals("Lire")
                    &&
                    expression2.expressions.size()==1 && !expression2.expressions.get(0).equals("Lire"))
            {

                expression=new Expression(Integer.parseInt(expression1.expressions.get(0))+
                        Integer.parseInt(expression2.expressions.get(0)));
            }

            return expression;
        }

        String op=termOp();

        Expression expression2=NontermExpr();

        reader.eat(Sym.RPAR);

        if(expression1!=null && expression2!=null)
        {
            if(expression1.expressions.size()==1 && !expression1.expressions.get(0).equals("Lire")
                    &&
                    expression2.expressions.size()==1 && !expression2.expressions.get(0).equals("Lire"))
            {
                if(op.equals("+"))
                {
                    expression=new Expression(Integer.parseInt(expression1.expressions.get(0))+
                            Integer.parseInt(expression2.expressions.get(0)));
                }
                else if(op.equals("-"))
                {
                    expression=new Expression(Integer.parseInt(expression1.expressions.get(0))-
                            Integer.parseInt(expression2.expressions.get(0)));
                }

            }
            else
            {
                LinkedList<String> exps=new LinkedList<>();


                for (String s1:expression1.expressions)
                {
                    exps.add(s1);
                }

                for (String s2:expression2.expressions)
                {
                    exps.add(s2);
                }



                expression=new Expression(exps);
            }

        }

    }


    else{
        throw new Exception();
    }
    return expression;

    }


    public Instruction NontermInstr() throws Exception{
        Expression exp=null;
        Instruction instruction=null;
        Program p=null;

      if(reader.check(Sym.AVANCER) ){

              reader.eat(Sym.AVANCER);
              reader.eat(Sym.LPAR);
              exp=NontermExpr();
              instruction=new Instruction("Avancer",exp);
          reader.eat(Sym.RPAR);

          return instruction;

      }

       if(reader.check(Sym.ECRIRE)){
           reader.eat(Sym.ECRIRE);
           reader.eat(Sym.LPAR);
           exp=NontermExpr();
           instruction=new Instruction("Ecrire",exp);
           reader.eat(Sym.RPAR);

           return instruction;

       }
      if (reader.check(Sym.TOURNER)){
          reader.eat(Sym.TOURNER);
          reader.eat(Sym.LPAR);
          exp= NontermExpr();
          instruction=new Instruction("Tourner",exp);
          reader.eat(Sym.RPAR);

          return instruction;


      }
      if(reader.check(Sym.SI)){
          reader.eat(Sym.SI);
          Condition condition=ntermcondition();
          reader.eat(Sym.ALORS);
          p=nontermProgram();
          instruction=new Instruction("Si",p,condition);
          reader.eat(Sym.FIN);
          return instruction;
      }
      if(reader.check(Sym.TANTQ)){
          reader.eat(Sym.TANTQ);
          Condition condition=ntermcondition();
          reader.eat(Sym.FAIRE);
          p=nontermProgram();
          instruction=new Instruction("TantQue",p,condition);
          reader.eat(Sym.FIN);

          return instruction;

      }
      else {
          throw new Exception();
      }

    }

    public Condition ntermcondition() throws Exception{
        Condition condition1,condition,condition2;
        Expression expression1,expression2;
        String c;
        if(reader.check(Sym.LIRE) || reader.check(Sym.INT)  || reader.check(Sym.LPAR)  ){
           expression1=NontermExpr();
           c=termcmp();
           expression2=NontermExpr();
           condition=new Condition(expression1,c,expression2);
           return condition;

        }
        if(reader.check(Sym.LACO)){
           reader.eat(Sym.LACO);
           condition1=ntermcondition();
           c=connecteur();
           condition2=ntermcondition();
           reader.eat(Sym.RACO);
           condition=new Condition(condition1,c,condition2);
           return condition;
        }
        else{
            throw new Exception();

        }

    }
    public String termcmp()throws Exception{
        if(reader.check(Sym.INF)){
          reader.eat(Sym.INF);
          return "<";
        }
        if(reader.check(Sym.SUP)){
            reader.eat(Sym.SUP);
            return ">";
        }
        if(reader.check(Sym.EQUAL)){
            reader.eat(Sym.EQUAL);
            return "=";
        }
        else {
            throw new Exception();
        }
    }
    public String connecteur() throws Exception{
        if(reader.check(Sym.ET)){
            reader.eat(Sym.ET);
            return "Et";
        }
        if(reader.check(Sym.OU)){
            reader.eat(Sym.OU);
            return "Ou";
        }
        else {
            throw new Exception();
        }

    }

    public String  termOp() throws Exception{
        if (reader.check(Sym.MOINS)){
            reader.eat(Sym.MOINS);
            return "-";

        }
        if( reader.check(Sym.PLUS)){
            reader.eat(Sym.PLUS);
            return "+";

        }
        else{
            throw new Exception();
        }

    }
    public int termInt() throws Exception{
      if (reader.check(Sym.INT)) {
          int val=reader.getIntValue();
          reader.eat(Sym.INT);
          return val;
        }
      else{
          throw new Exception();
      }

    }




}

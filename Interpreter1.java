import java.util.LinkedList;

public class Interpreter1 implements Interpreter {

    int indicator;

    @Override
    public void run(Program prog, Grid grid)
    {

        Instruction ins=prog.getInstruction();

        deroulerInstruction(ins,grid);

        if(prog.getProgram()!=null)
        {
            run(prog.getProgram(),grid);

        }


    }

    
    public void run2(Program prog, Grid grid)
    {

        Instruction ins=prog.getInstruction();

        deroulerInstruction(ins,grid);

    }

    public boolean verifierCondition(Condition c,Grid gride){
     if(c.condition1!=null){
         switch (c.c){
             case "Et":
                 return (verifierCondition(c.condition1,gride) && verifierCondition(c.condition2,gride));
             case "Ou":
                 return (verifierCondition(c.condition1,gride) || verifierCondition(c.condition2,gride));

         }
     }
     else{
       return  verifierexpression(c.expression1,c.c,c.expression2,gride);
     }

      return true;
    }

    public boolean verifierexpression(Expression e1,String c,Expression e2,Grid grid){
        int val1=donnerVal(e1,grid);
        int val2=donnerVal(e2,grid);
        switch (c){
            case "<":
                if(val1<val2){
                    return true;
                }
                return false;
            case ">":
                if(val1>val2){
                    return true;
                }
                return false;
            case "=":
                if(val1==val2){
                    return true;
                }
                return false;
        }
        return false;
    }

    public int donnerVal(Expression e,Grid grid){
        int d=0;
        for (String s : e.expressions) {
            if (s.equals("Lire")) {
                d += grid.lire();
            } else {
                int n = Integer.parseInt(s);
                d += n;

            }
        }
        return d;
    }

    public void deroulerInstruction(Instruction ins, Grid grid)
    {
        String action=ins.action;
        String affich=action+"(";
        LinkedList<String> exp;


        switch (action)
        {
            case "Si":
                if(ins.condition!=null && ins.program!=null){
                    Condition c=ins.condition;
                    if(verifierCondition(c,grid)){
                        run(ins.program,grid);
                    }
                }

                break;
            case "TantQue":
                if(ins.condition!=null && ins.program!=null) {

                while(verifierCondition(ins.condition,grid)){
                    run(ins.program,grid);
                }

                }

                    break;
            case "Tourner":
                if(ins.expression!=null && ins.expression.expressions!=null ) {
                    exp = ins.expression.expressions;
                    int tourne = 0;

                    if (exp != null) {
                        for (String s : exp) {
                            if (s.equals("Lire")) {
                                tourne += grid.lire();
                                affich += "Lire";
                            } else {
                                int n = Integer.parseInt(s);
                                tourne += n;
                                affich += n + ")";

                            }
                        }


                        grid.tourner(tourne);
                        indicator=tourne;
                        System.out.println(" Tourner :" + tourne);

                    }

                }
                break;
            case "Avancer":

                if(ins.expression!=null && ins.expression.expressions!=null ) {
                    exp = ins.expression.expressions;
                    int avance = 0;

                    if (exp != null) {
                        for (String s : exp) {
                            if (s.equals("Lire")) {

                                affich += "Lire)";
                                avance += grid.lire();
                            } else {
                                int n = Integer.parseInt(s);
                                affich += n + ")";
                                avance += n;
                            }


                        }

                        grid.avancer(avance);
                        indicator=avance;
                        System.out.println("Avancer :" + avance);

                    }
                }
                break;
            case "Ecrire":
                if(ins.expression!=null && ins.expression.expressions!=null ) {
                    exp = ins.expression.expressions;
                    int ecrire = 0;

                    if (exp != null) {
                        for (String s : exp) {
                            if (s.equals("Lire")) {
                                affich += "Lire";
                                ecrire += grid.lire();
                            } else {
                                int n = Integer.parseInt(s);
                                affich += n + ")";
                                ecrire += n;
                            }
                        }


                        grid.ecrire(ecrire);
                        indicator=ecrire;
                        System.out.println(" Ecrire :" + ecrire);


                    }
                }
                break;



        }

        System.out.println(affich);



    }




}


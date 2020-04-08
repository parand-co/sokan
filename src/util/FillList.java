package util;

import amar.model.Personel;
import dataBaseModel.authentication.form.Form;
import dataBaseModel.authentication.permission.Permission;
import dataBaseModel.authentication.subPermission.SubPermission;
import dataBaseModel.authentication.user.User;
import dataBaseModel.baseTable.Bakhsh;
import dataBaseModel.baseTable.Dayere;
import dataBaseModel.baseTable.NoeEstekhdam;
import dataBaseModel.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class FillList {

    public List<NoeEstekhdam> noeEstekhdams(Permission permissions) {
        List<NoeEstekhdam> noeEstekhdams = new ArrayList<>();
        List<SubPermission> subPermissions = subPermissions(permissions);
        if(subPermissions.size() > 0) {
            if (subPermissions.get(0).getNoePersonel() != null) {
                for (SubPermission subPermission : subPermissions) {
                    if(!noeEstekhdams.contains(subPermission.getNoePersonel())) {
                        noeEstekhdams.add(subPermission.getNoePersonel());
                    }
                }
            } else {
                Session session = HibernateUtil.getSession();
                noeEstekhdams = session.createQuery("from NoeEstekhdam ").list();
                session.close();
            }
        }
        return noeEstekhdams;
    }

    public List<Form> forms(User user) {
        Session session = HibernateUtil.getSession();
        List<Permission> permissions = session.createQuery("FROM Permission WHERE user.id = ?").setLong(0, user.getId()).list();
        session.close();

        List<Form> forms = new ArrayList<>();

        for (Permission permission : permissions) {
            forms.add(permission.getForm());
        }

        return forms;
    }

    public List<Dayere> dayeres(Permission permissions) {
        List<Dayere> dayeres = new ArrayList<>();

        List<SubPermission> subPermissions = subPermissions(permissions);

        if(subPermissions.size() > 0){
            if (subPermissions.get(0).getDayere().getCode().equals("0000")) {
                Session session = HibernateUtil.getSession();
                dayeres = session.createQuery("from Dayere WHERE code != '0000'").list();
                session.close();
            } else {
                for (SubPermission subPermission : subPermissions) {
                    if(!dayeres.contains(subPermission.getDayere())) {
                        dayeres.add(subPermission.getDayere());
                    }
                }
            }
        }

        return dayeres;
    }

    public List<Dayere> dayeresForAdmin(Permission permissions) {
        List<Dayere> dayeres = new ArrayList<>();

        List<SubPermission> subPermissions = subPermissions(permissions);

        if(subPermissions.size() > 0){
            if (subPermissions.get(0).getDayere().getCode().equals("0000")) {
                Session session = HibernateUtil.getSession();
                dayeres = session.createQuery("from Dayere").list();
                session.close();
            } else {
                for (SubPermission subPermission : subPermissions) {
                    if(!dayeres.contains(subPermission.getDayere())) {
                        dayeres.add(subPermission.getDayere());
                    }
                }
            }
        }

        return dayeres;
    }

    public List<Bakhsh> bakhsh(Permission permissions) {
        List<Bakhsh> bakhshes = new ArrayList<>();
        List<SubPermission> subPermissions = subPermissions(permissions);
        if(subPermissions.size() > 0) {
            if (subPermissions.get(0).getBakhsh() != null) {
                for (SubPermission subPermission : subPermissions) {
                    if(!bakhshes.contains(subPermission.getBakhsh())) {
                        bakhshes.add(subPermission.getBakhsh());
                    }
                }
            } else {
                if (subPermissions.get(0).getDayere().getCode().equals("0000")){
                    StringBuilder query = new StringBuilder("from Bakhsh");
                    Session session = HibernateUtil.getSession();
                    bakhshes.addAll(session.createQuery(query.toString()).list());
                    session.close();
                } else {
                    StringBuilder query = new StringBuilder("from Bakhsh WHERE");
                    for (SubPermission subPermission : subPermissions) {
                        if(query.toString().equals("from Bakhsh WHERE")){
                            query.append(" dayere.id = ").append(subPermission.getDayere().getId());
                        } else {
                            query.append(" OR dayere.id = ").append(subPermission.getDayere().getId());
                        }
                    }

                    Session session = HibernateUtil.getSession();
                    List<Bakhsh> items = session.createQuery(query.toString()).list();
                    session.close();

                    for (Bakhsh item : items) {
                        if(!bakhshes.contains(item)) {
                            bakhshes.add(item);
                        }
                    }
                }
            }
        }
        return bakhshes;
    }

    public List<Bakhsh> bakhshByDayereCode(Permission permissions, String dayereCode) {
        List<Bakhsh> bakhshes = new ArrayList<>();
        List<SubPermission> subPermissions = subPermissions(permissions);
        if(subPermissions.size() > 0) {
            if (subPermissions.get(0).getBakhsh() != null) {
                for (SubPermission subPermission : subPermissions) {
                    if(!bakhshes.contains(subPermission.getBakhsh())) {
                        if(subPermission.getDayere() != null) {
                            if(subPermission.getDayere().getCode().equals(dayereCode)) {
                                bakhshes.add(subPermission.getBakhsh());
                            }
                        }
                    }
                }
            } else {
                List<Bakhsh> items;
                if (subPermissions.get(0).getDayere().getCode().equals("0000")){
                    StringBuilder query = new StringBuilder("from Bakhsh");
                    Session session = HibernateUtil.getSession();
                    items = session.createQuery(query.toString()).list();
                    session.close();
                } else {
                    StringBuilder query = new StringBuilder("from Bakhsh WHERE");
                    for (SubPermission subPermission : subPermissions) {
                        if(query.toString().equals("from Bakhsh WHERE")){
                            query.append(" dayere.id = ").append(subPermission.getDayere().getId());
                        } else {
                            query.append(" OR dayere.id = ").append(subPermission.getDayere().getId());
                        }
                    }

                    Session session = HibernateUtil.getSession();
                    items = session.createQuery(query.toString()).list();
                    session.close();
                }

                for (Bakhsh item : items) {
                    if(!bakhshes.contains(item)) {
                        if(item.getDayere() != null) {
                            if(item.getDayere().getCode().equals(dayereCode)) {
                                bakhshes.add(item);
                            }
                        }
                    }
                }
            }
        }
        return bakhshes;
    }


    public List<Personel> personels(Permission permissions, String shomarePersoneli, String codeMeli, String cart, String where) {
        List<Personel> personels;

        List<SubPermission> subPermissions = subPermissions(permissions);

        StringBuilder query = new StringBuilder("FROM Personel WHERE 1 = 1 ");

        if (shomarePersoneli != null && !shomarePersoneli.equals("")) {
            query.append(" AND shomarePersoneli = '").append(shomarePersoneli).append("'");
        }
        if (codeMeli != null && !codeMeli.equals("")) {
            query.append(" AND codeMeli = '").append(codeMeli).append("'");
        }
        if (cart != null && !cart.equals("")) {
            query.append(" AND shomareKart = '").append(cart).append("'");
        }

        StringBuilder noePersonel = new StringBuilder("(");
        StringBuilder dayere = new StringBuilder("(");
        StringBuilder bakhsh = new StringBuilder("(");
        for (SubPermission subPermission : subPermissions) {
            if (subPermission.getNoePersonel() != null) {
                if (noePersonel.toString().equals("(")) {
                    noePersonel.append("noeEstekhdam.id = ").append(subPermission.getNoePersonel().getId()).append(" ");
                } else {
                    if (!noePersonel.toString().equals("(")) {
                        noePersonel.append(" OR noeEstekhdam.id = ").append(subPermission.getNoePersonel().getId()).append(" ");
                    }
                }
            }

            if (!subPermission.getDayere().getCode().equals("0000")) {
                if (dayere.toString().equals("(")) {
                    dayere.append("dayere.id = ").append(subPermission.getDayere().getId()).append(" ");
                } else {
                    if (!dayere.toString().equals("(")) {
                        dayere.append(" OR dayere.id = ").append(subPermission.getDayere().getId()).append(" ");
                    }
                }
            }

            if (subPermission.getBakhsh() != null) {
                if (bakhsh.toString().equals("(")) {
                    bakhsh.append("bakhsh.id = ").append(subPermission.getBakhsh().getId()).append(" ");
                } else {
                    if (!bakhsh.toString().equals("(")) {
                        bakhsh.append(" OR bakhsh.id = ").append(subPermission.getBakhsh().getId()).append(" ");
                    }
                }
            }
        }

        if (!noePersonel.toString().equals("(")) {
            query.append(" AND ").append(noePersonel.toString()).append(")");
        }

        if (!dayere.toString().equals("(")) {
            query.append(" AND ").append(dayere.toString()).append(")");
        }

        if (!bakhsh.toString().equals("(")) {
            query.append(" AND ").append(bakhsh.toString()).append(")");
        }

        if (where != null && !where.equals("")) {
            query.append(where);
        }

        Session session1 = HibernateUtil.getSession();
        personels = session1.createQuery(query.toString()).list();
        session1.close();

        return personels;
    }

    private List<SubPermission> subPermissions(Permission permissions) {
        Session session = HibernateUtil.getSession();
        List<SubPermission> subPermissions = session.createQuery("FROM SubPermission WHERE permission.id = ?").setLong(0, permissions.getId()).list();
        session.close();

        return subPermissions;
    }
}
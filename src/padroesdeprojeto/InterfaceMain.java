package padroesdeprojeto;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfaceMain extends JFrame {

    public InterfaceMain() {
        setTitle("Agenda de Contatos");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        JButton botaoAdicionar = new JButton("Adicionar Contato");
        botaoAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                JFrame janelaAdicionarContato = new JFrame("Adicionar Contato");
                janelaAdicionarContato.setSize(400, 300);
                janelaAdicionarContato.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel painel = new JPanel();
                painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
   
                JTextField campoNome = new JTextField(20);
                JTextField campoEmail = new JTextField(20);
                JTextField campoEndereco = new JTextField(20);
                JTextField campoDataNascimento = new JTextField(10);
                painel.add(new JLabel("Nome:"));
                painel.add(campoNome);
                painel.add(new JLabel("E-mail:"));
                painel.add(campoEmail);
                painel.add(new JLabel("Endereço:"));
                painel.add(campoEndereco);
                painel.add(new JLabel("Data de Nascimento (dd/mm/aaaa):"));
                painel.add(campoDataNascimento);
                
                JButton botaoSalvar = new JButton("Salvar Contato");
                botaoSalvar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Contato contato = new Contato();
                        contato.setNome(campoNome.getText());
                        contato.setEmail(campoEmail.getText());
                        contato.setEndereco(campoEndereco.getText());
                        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Calendar dataNascimento = Calendar.getInstance();
                            dataNascimento.setTime(formato.parse(campoDataNascimento.getText()));
                            contato.setDataNascimento(dataNascimento);
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null, "Data de Nascimento inválida! Use o formato dd/mm/aaaa.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                     
                        ContatoDAO dao = new ContatoDAO();
                        dao.insert(contato);
                        JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                     
                        janelaAdicionarContato.dispose();
                    }
                });
                painel.add(botaoSalvar);               
                janelaAdicionarContato.add(painel);
                janelaAdicionarContato.setVisible(true);
            }
        });

        JButton botaoListar = new JButton("Listar Contatos");
        botaoListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ContatoDAO dao = new ContatoDAO();
                List<Contato> contatos = dao.getAll();

                JPanel painelLista = new JPanel(new GridLayout(contatos.size(), 3));
                for (Contato contato : contatos) {
                    JLabel labelNome = new JLabel(contato.getNome());
                    painelLista.add(labelNome);

                    JButton botaoEditar = new JButton("Editar Contato");
                    botaoEditar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                            JFrame janelaEditarContato = new JFrame("Editar Contato");
                            janelaEditarContato.setSize(400, 300);
                            janelaEditarContato.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                            JPanel painelEditarContato = new JPanel(new GridLayout(5, 2));

                            JTextField campoNome = new JTextField(contato.getNome());
                            painelEditarContato.add(new JLabel("Nome:"));
                            painelEditarContato.add(campoNome);

                            JTextField campoEmail = new JTextField(contato.getEmail());
                            painelEditarContato.add(new JLabel("E-mail:"));
                            painelEditarContato.add(campoEmail);

                            JTextField campoEndereco = new JTextField(contato.getEndereco());
                            painelEditarContato.add(new JLabel("Endereço:"));
                            painelEditarContato.add(campoEndereco);

                            JTextField campoDataNascimento = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(contato.getDataNascimento().getTime()));
                            painelEditarContato.add(new JLabel("Data de Nascimento (dd/mm/aaaa):"));
                            painelEditarContato.add(campoDataNascimento);

                            JButton botaoAtualizar = new JButton("Atualizar Contato");
                            botaoAtualizar.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    contato.setNome(campoNome.getText());
                                    contato.setEmail(campoEmail.getText());
                                    contato.setEndereco(campoEndereco.getText());
                                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                                    try {
                                        Calendar dataNascimento = Calendar.getInstance();
                                        dataNascimento.setTime(formato.parse(campoDataNascimento.getText()));
                                        contato.setDataNascimento(dataNascimento);
                                    } catch (ParseException ex) {
                                        JOptionPane.showMessageDialog(null, "Data de Nascimento inválida! Use o formato dd/mm/aaaa.", "Erro", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                    dao.update(contato);
                                    JOptionPane.showMessageDialog(null, "Contato atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                                    janelaEditarContato.dispose();
                                }
                            });
                            
                            JButton botaoExcluir = new JButton("Excluir Contato");
                            botaoExcluir.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    ContatoDAO dao = new ContatoDAO();
                                    int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o contato '" + contato.getNome() + "'?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                                    if (resposta != JOptionPane.YES_OPTION) {
                                        return;
                                    }

                                    dao.delete(contato.getId());
                                    JOptionPane.showMessageDialog(null, "Contato excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                }
                            });
                            
                            painelEditarContato.add(botaoAtualizar);
                            painelEditarContato.add(botaoExcluir);
                            janelaEditarContato.add(painelEditarContato);
                            janelaEditarContato.setVisible(true);
                        }
                    });
                    painelLista.add(botaoEditar);
                }

                JFrame janelaListaContatos = new JFrame("Lista de Contatos");
                janelaListaContatos.add(painelLista);
                janelaListaContatos.setSize(400, 300);
                janelaListaContatos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                janelaListaContatos.setVisible(true);
            }
        });
                    
        painel.add(botaoAdicionar);
        painel.add(botaoListar);
        add(painel);
    }

    public static void main(String[] args) {
    	InterfaceMain tela = new InterfaceMain();
        tela.setVisible(true);
    }
}

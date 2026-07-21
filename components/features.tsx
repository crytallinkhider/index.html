import { Shield, Bot, Users, Zap, LineChart, Lock } from "lucide-react"

const features = [
  {
    icon: Shield,
    title: "Bypass avançado",
    description:
      "Contorne detecções com um sistema atualizado diariamente e mantido por uma equipe dedicada.",
    color: "text-primary",
  },
  {
    icon: Bot,
    title: "Automação inteligente",
    description:
      "Configure rotinas automáticas para farm, tarefas e ações repetitivas em segundos.",
    color: "text-accent",
  },
  {
    icon: Users,
    title: "Gestão de contas",
    description:
      "Gerencie múltiplas contas em uma única interface, com troca instantânea e organização por tags.",
    color: "text-teal",
  },
  {
    icon: Zap,
    title: "Performance extrema",
    description:
      "Execução leve e otimizada que não pesa no seu sistema, mesmo com dezenas de contas ativas.",
    color: "text-primary",
  },
  {
    icon: LineChart,
    title: "Métricas em tempo real",
    description:
      "Acompanhe o desempenho de cada conta com painéis e estatísticas detalhadas ao vivo.",
    color: "text-accent",
  },
  {
    icon: Lock,
    title: "Segurança em primeiro lugar",
    description:
      "Criptografia de ponta a ponta e proteção total dos seus dados e credenciais.",
    color: "text-teal",
  },
]

export function Features() {
  return (
    <section id="recursos" className="relative px-4 py-24">
      <div className="mx-auto max-w-6xl">
        <div className="mx-auto max-w-2xl text-center">
          <span className="text-sm font-medium text-primary">Recursos</span>
          <h2 className="mt-2 text-balance text-3xl font-bold tracking-tight md:text-4xl">
            Tudo o que você precisa em um só painel
          </h2>
          <p className="mt-4 text-pretty leading-relaxed text-muted-foreground">
            Ferramentas profissionais reunidas para você dominar a automação com
            controle total e tranquilidade.
          </p>
        </div>

        <div className="mt-14 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {features.map((feature) => (
            <div
              key={feature.title}
              className="group rounded-2xl border border-border bg-card/50 p-6 backdrop-blur-sm transition-colors hover:bg-card"
            >
              <span className="mb-4 inline-flex h-11 w-11 items-center justify-center rounded-xl border border-border bg-muted">
                <feature.icon className={`h-5 w-5 ${feature.color}`} />
              </span>
              <h3 className="text-lg font-semibold">{feature.title}</h3>
              <p className="mt-2 text-sm leading-relaxed text-muted-foreground">
                {feature.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}

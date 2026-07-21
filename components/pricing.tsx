import { Check } from "lucide-react"

const plans = [
  {
    name: "Básico",
    price: "R$ 29",
    period: "/mês",
    description: "Para quem está começando.",
    features: ["Até 5 contas", "Bypass essencial", "Automação básica", "Suporte por e-mail"],
    highlighted: false,
  },
  {
    name: "Pro",
    price: "R$ 79",
    period: "/mês",
    description: "O favorito da comunidade.",
    features: [
      "Até 50 contas",
      "Bypass avançado",
      "Automação completa",
      "Métricas em tempo real",
      "Suporte prioritário 24/7",
    ],
    highlighted: true,
  },
  {
    name: "Enterprise",
    price: "R$ 199",
    period: "/mês",
    description: "Para operações em larga escala.",
    features: [
      "Contas ilimitadas",
      "Bypass premium dedicado",
      "API de automação",
      "Gerente de conta exclusivo",
      "SLA garantido",
    ],
    highlighted: false,
  },
]

export function Pricing() {
  return (
    <section id="planos" className="relative px-4 py-24">
      <div className="mx-auto max-w-6xl">
        <div className="mx-auto max-w-2xl text-center">
          <span className="text-sm font-medium text-teal">Planos</span>
          <h2 className="mt-2 text-balance text-3xl font-bold tracking-tight md:text-4xl">
            Escolha o plano perfeito para você
          </h2>
          <p className="mt-4 text-pretty leading-relaxed text-muted-foreground">
            Sem contratos, sem surpresas. Cancele quando quiser.
          </p>
        </div>

        <div className="mt-14 grid gap-6 lg:grid-cols-3">
          {plans.map((plan) => (
            <div
              key={plan.name}
              className={`relative flex flex-col rounded-2xl border p-8 backdrop-blur-sm ${
                plan.highlighted
                  ? "border-primary bg-card"
                  : "border-border bg-card/50"
              }`}
            >
              {plan.highlighted && (
                <span className="absolute -top-3 left-1/2 -translate-x-1/2 rounded-full bg-primary px-3 py-1 text-xs font-semibold text-primary-foreground">
                  Mais popular
                </span>
              )}
              <h3 className="text-lg font-semibold">{plan.name}</h3>
              <p className="mt-1 text-sm text-muted-foreground">{plan.description}</p>
              <div className="mt-4 flex items-baseline gap-1">
                <span className="text-4xl font-bold">{plan.price}</span>
                <span className="text-sm text-muted-foreground">{plan.period}</span>
              </div>

              <ul className="mt-6 flex flex-col gap-3">
                {plan.features.map((feature) => (
                  <li key={feature} className="flex items-center gap-2 text-sm">
                    <Check className="h-4 w-4 shrink-0 text-teal" />
                    <span className="text-muted-foreground">{feature}</span>
                  </li>
                ))}
              </ul>

              <a
                href="#"
                className={`mt-8 rounded-full px-6 py-3 text-center text-sm font-semibold transition-transform hover:scale-105 ${
                  plan.highlighted
                    ? "bg-primary text-primary-foreground"
                    : "border border-border bg-muted text-foreground"
                }`}
              >
                Assinar {plan.name}
              </a>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}

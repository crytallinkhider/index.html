const steps = [
  {
    number: "01",
    title: "Crie sua conta",
    description: "Cadastre-se em segundos e escolha o plano ideal para o seu uso.",
  },
  {
    number: "02",
    title: "Conecte suas contas",
    description: "Adicione e organize todas as suas contas Roblox no painel central.",
  },
  {
    number: "03",
    title: "Configure a automação",
    description: "Ative bypass, defina rotinas e deixe o sistema trabalhar por você.",
  },
  {
    number: "04",
    title: "Acompanhe os resultados",
    description: "Monitore métricas em tempo real e otimize sua operação continuamente.",
  },
]

export function HowItWorks() {
  return (
    <section id="como-funciona" className="relative px-4 py-24">
      <div className="mx-auto max-w-6xl">
        <div className="mx-auto max-w-2xl text-center">
          <span className="text-sm font-medium text-accent">Como funciona</span>
          <h2 className="mt-2 text-balance text-3xl font-bold tracking-tight md:text-4xl">
            Comece em quatro passos simples
          </h2>
        </div>

        <div className="mt-14 grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          {steps.map((step) => (
            <div
              key={step.number}
              className="rounded-2xl border border-border bg-card/50 p-6 backdrop-blur-sm"
            >
              <span className="text-3xl font-bold text-primary/40">{step.number}</span>
              <h3 className="mt-4 text-lg font-semibold">{step.title}</h3>
              <p className="mt-2 text-sm leading-relaxed text-muted-foreground">
                {step.description}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}

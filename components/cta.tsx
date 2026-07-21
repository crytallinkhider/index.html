import { ArrowRight } from "lucide-react"

export function Cta() {
  return (
    <section className="relative px-4 py-24">
      <div className="mx-auto max-w-4xl overflow-hidden rounded-3xl border border-border bg-card/60 p-10 text-center backdrop-blur-md md:p-16">
        <div
          className="pointer-events-none absolute inset-0 bg-gradient-to-br from-primary/15 via-transparent to-accent/15"
          aria-hidden="true"
        />
        <div className="relative z-10">
          <h2 className="text-balance text-3xl font-bold tracking-tight md:text-4xl">
            Pronto para dominar a automação?
          </h2>
          <p className="mx-auto mt-4 max-w-xl text-pretty leading-relaxed text-muted-foreground">
            Junte-se a milhares de usuários que já elevaram o nível com o Immortal
            Dashboard. Comece hoje mesmo.
          </p>
          <a
            href="#planos"
            className="group mt-8 inline-flex items-center gap-2 rounded-full bg-primary px-7 py-3.5 text-sm font-semibold text-primary-foreground transition-transform hover:scale-105"
          >
            Acessar o painel
            <ArrowRight className="h-4 w-4 transition-transform group-hover:translate-x-1" />
          </a>
        </div>
      </div>
    </section>
  )
}

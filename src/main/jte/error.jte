@import gg.jte.Content

@param int status = 500
@param String message = null

!{String title = status == 404 ? "Page Not Found" : status == 409 ? "Conflict" : "Something Went Wrong";}
!{String subtitle = status == 404 ? "The page you're looking for doesn't exist." : status == 409 ? "There was a conflict with your request." : "An unexpected error occurred. Please try again.";}

@template.layout(title = title, content = @`
    <div style="text-align: center; padding: 6rem 2rem;">
        <div style="font-family: var(--font-heading), serif; font-size: 6rem; color: var(--accent); line-height: 1; margin-bottom: 1rem;">
            ${status}
        </div>
        <h1 style="font-family: var(--font-heading), serif; font-size: 2rem; font-weight: 400; color: var(--text); margin-bottom: 1rem;">
            ${title}
        </h1>
        <p style="color: var(--text-muted); font-size: 0.95rem; margin-bottom: 2.5rem;">
            ${subtitle}
        </p>
        @if(message != null && !message.isBlank())
            <p style="color: var(--text-muted); font-size: 0.85rem; margin-bottom: 2.5rem; font-style: italic;">
                ${message}
            </p>
        @endif
        <a href="/" class="btn btn-primary">Back to Movies</a>
    </div>
`)
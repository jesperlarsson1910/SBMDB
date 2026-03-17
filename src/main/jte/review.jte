@import org.example.sbmdb.entity.dto.ReviewDTO
@import gg.jte.Content

@param ReviewDTO review

!{String editedLabel = !review.reviewDate().equals(review.reviewUpdateDate()) ? "edited " + review.reviewUpdateDate().toString() : "";}

@template.layout(title = review.reviewAuthor() + " on " + review.movieTitle(), content = @`

    <div style="margin-bottom: 0.75rem;">
        <a href="/movies/${review.movieId()}"
           style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.08em;">
            ← Back to ${review.movieTitle()}
        </a>
    </div>

    <div class="page-header">
        <h1 class="page-title">${review.movieTitle()}</h1>
    </div>

    <div class="card" style="max-width: 680px;">

        <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 1.25rem;">
            <div>
                <div style="font-weight: 500; font-size: 1rem; color: var(--text); margin-bottom: 0.25rem;">
                    ${review.reviewAuthor()}
                </div>
                <div style="font-size: 0.78rem; color: var(--text-muted);">
                    ${String.valueOf(review.reviewDate())}
                    @if(!editedLabel.isBlank())
                        <span style="margin-left: 0.5rem;">(${editedLabel})</span>
                    @endif
                </div>
            </div>
            <div style="display: flex; align-items: center; gap: 0.75rem;">
                <div style="position: relative; display: inline-block; font-size: 1.2rem; letter-spacing: 0.15em;">
                    <span style="color: var(--border);">★★★★★</span>
                    <span style="position: absolute; left: 0; top: 0; overflow: hidden; width: ${String.valueOf(review.reviewRating() * 20)}%;">
                        <span style="color: var(--accent);">★★★★★</span>
                    </span>
                </div>
                <span style="font-size: 1rem; color: var(--text-muted);">${review.reviewRating()} / 5</span>
            </div>
        </div>

        <hr class="divider"/>

        @if(review.reviewText() != null && !review.reviewText().isBlank())
            <p style="font-size: 0.95rem; color: var(--text-muted); line-height: 1.75;">
                ${review.reviewText()}
            </p>
        @else
            <p style="font-size: 0.875rem; color: var(--text-muted); font-style: italic;">
                No written review provided.
            </p>
        @endif

        <div style="display: flex; gap: 0.75rem; margin-top: 1.5rem; padding-top: 1rem; border-top: 1px solid var(--border);">
            <a href="/reviews/${review.id()}/edit" class="btn btn-ghost">Edit</a>
            <form method="post" action="/reviews/${review.id()}/delete" style="margin: 0;">
                <button type="submit" class="btn btn-danger">Delete</button>
            </form>
        </div>
    </div>
`)
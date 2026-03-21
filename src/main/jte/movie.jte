@import org.example.sbmdb.entity.dto.MovieDTO
@import org.example.sbmdb.entity.dto.ReviewDTO
@import gg.jte.Content

@param MovieDTO movie
@param String currentSort = "reviewDate,desc"

@template.layout(title = "${movie.title()}", content = @`

    <div style="margin-bottom: 0.75rem;">
        <a href="/" style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.08em;">← Back to Movies</a>
    </div>

    <div style="display: grid; grid-template-columns: 1fr auto; align-items: start; gap: 2rem; margin-bottom: 2rem; padding-bottom: 2rem; border-bottom: 1px solid var(--border);">
        <div>
            <h1 style="font-family: var(--font-heading), serif; font-size: 2.75rem; font-weight: 400; color: var(--text); margin-bottom: 0.5rem;">
                ${movie.title()}
            </h1>
            <div style="font-size: 0.9rem; color: var(--text-muted); margin-bottom: 1.25rem;">
                ${String.join(", ", movie.directors())} · ${movie.releaseYear().getYear()} · ${movie.runningTime()} min
            </div>
            @if(movie.rating() != null)
                <div style="display: flex; align-items: center; gap: 0.75rem; margin-bottom: 1.25rem;">
                    <div style="position: relative; display: inline-block; font-size: 1.2rem; letter-spacing: 0.15em;">
                        <span style="color: var(--border);">★★★★★</span>
                        <span style="position: absolute; left: 0; top: 0; overflow: hidden; width: ${String.format(java.util.Locale.US, "%.1f", movie.rating() / 5.0 * 100)}%;">
                            <span style="color: var(--accent);">★★★★★</span>
                        </span>
                    </div>
                    <span style="font-size: 1rem; color: var(--text-muted);">${String.format(java.util.Locale.US, "%.1f", movie.rating())} / 5.0</span>
                    <span style="font-size: 0.8rem; color: var(--text-muted);">(${movie.reviews().size()} reviews)</span>
                </div>
            @else
                <div style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 1.25rem;">No ratings yet</div>
            @endif
            @if(movie.description() != null && !movie.description().isBlank())
                <p style="font-size: 0.95rem; color: var(--text-muted); line-height: 1.7; max-width: 680px;">
                    ${movie.description()}
                </p>
            @endif
        </div>

        <div style="display: flex; gap: 0.75rem;">
            <a href="/movies/${movie.id()}/edit" class="btn btn-ghost">Edit</a>
            <form method="post" action="/movies/${movie.id()}/delete" style="margin: 0;">
                <button type="submit" class="btn btn-danger"
                        onclick="return confirm('Are you sure you want to delete ${movie.title()}? This will also delete all its reviews.')">
                    Delete
                </button>
            </form>
        </div>
    </div>

    <div style="display: flex; justify-content: space-between; align-items: baseline; margin-bottom: 1.5rem;">
        <h2 style="font-family: var(--font-heading), serif; font-size: 1.5rem; font-weight: 400;">Reviews</h2>
        <div style="display: flex; gap: 0.75rem; align-items: stretch;">
            <form method="get" action="/movies/${movie.id()}" style="margin: 0;">
                <select name="sort" onchange="this.form.submit()" style="width: 150px; height: 100%;">
                    <option value="reviewDate,desc" selected="${currentSort.equals("reviewDate,desc")}">Newest First</option>
                    <option value="reviewDate,asc" selected="${currentSort.equals("reviewDate,asc")}">Oldest First</option>
                    <option value="reviewRating,desc" selected="${currentSort.equals("reviewRating,desc")}">Highest Rated</option>
                    <option value="reviewRating,asc" selected="${currentSort.equals("reviewRating,asc")}">Lowest Rated</option>
                </select>
            </form>
            <a href="/movies/${movie.id()}/review" class="btn btn-primary" style="align-self: stretch; display: flex; align-items: center;">+ Add Review</a>
        </div>
    </div>

    @if(movie.reviews().isEmpty())
        <div class="empty">
            <div class="empty-title">No reviews yet</div>
            <p>Be the first to review this movie.</p>
        </div>
    @else
        <div style="display: flex; flex-direction: column; gap: 1rem;">
            @for(ReviewDTO review : movie.reviews())
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 0.75rem;">
                        <div>
                            <a href="/reviews/${review.id()}" style="font-weight: 500; font-size: 0.9rem; color: var(--text);">
                                ${review.reviewAuthor()}
                            </a>
                            <span style="font-size: 0.73rem; color: var(--text-muted); margin-left: 0.75rem;">
                                ${String.valueOf(review.reviewUpdateDate())}
                                @if(!review.reviewDate().equals(review.reviewUpdateDate()))
                                    <span style="color: var(--text-muted);">(posted ${String.valueOf(review.reviewDate())})</span>
                                @endif
                            </span>
                        </div>
                        <div style="display: flex; align-items: center; gap: 0.5rem;">
                            <div style="position: relative; display: inline-block; font-size: 0.85rem; letter-spacing: 0.1em;">
                                <span style="color: var(--border);">★★★★★</span>
                                <span style="position: absolute; left: 0; top: 0; overflow: hidden; width: ${review.reviewRating() * 20}%;">
                                    <span style="color: var(--accent);">★★★★★</span>
                                </span>
                            </div>
                            <span style="font-size: 0.8rem; color: var(--text-muted);">${review.reviewRating()}/5</span>
                        </div>
                    </div>
                    @if(review.reviewText() != null && !review.reviewText().isBlank())
                        <p style="font-size: 0.875rem; color: var(--text-muted); line-height: 1.7;">${review.reviewText()}</p>
                    @endif
                    <div style="display: flex; gap: 0.5rem; margin-top: 1rem; padding-top: 0.75rem; border-top: 1px solid var(--border);">
                        <a href="/reviews/${review.id()}/edit" class="btn btn-ghost" style="font-size: 0.72rem; padding: 0.35rem 0.85rem;">Edit</a>
                        <form method="post" action="/reviews/${review.id()}/delete" style="margin: 0;">
                            <button type="submit" class="btn btn-danger" style="font-size: 0.72rem; padding: 0.35rem 0.85rem;"
                                    onclick="return confirm('Are you sure you want to delete this review by ${review.reviewAuthor()}?')">
                                Delete
                            </button>
                        </form>
                    </div>
                </div>
            @endfor
        </div>
    @endif
`)
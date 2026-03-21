@import org.example.sbmdb.entity.dto.ReviewDTO
@import org.example.sbmdb.filter.ReviewFilter
@import org.springframework.data.domain.Page
@import gg.jte.Content

@param Page<ReviewDTO> reviews
@param ReviewFilter filter
@param String error = null
@param String currentSort = "reviewDate,desc"
@param org.springframework.data.domain.Pageable pageable

@template.layout(title = "Reviews", content = @`

    !{String query = "";}
    !{if (filter.author() != null && !filter.author().isBlank()) query += "&author=" + filter.author();}
    !{if (filter.movieId() != null) query += "&movieId=" + filter.movieId();}
    !{if (filter.ratingMin() != null) query += "&ratingMin=" + filter.ratingMin();}
    !{if (filter.ratingMax() != null) query += "&ratingMax=" + filter.ratingMax();}
    !{String sortQuery = pageable.getSort().isSorted() ? "&sort=" + pageable.getSort().toString().replace(": ", ",") : "";}

    <div class="page-header">
        <h1 class="page-title">Reviews</h1>
        <span style="color: var(--text-muted); font-size: 0.85rem;">${reviews.getTotalElements()} reviews</span>
    </div>

    @if(error != null)
        <div style="
            background: rgba(192, 57, 43, 0.1);
            border: 1px solid var(--danger);
            color: var(--danger);
            padding: 0.75rem 1.25rem;
            border-radius: 3px;
            margin-bottom: 1.25rem;
            font-size: 0.875rem;">
            ${error}
        </div>
    @endif

    <form class="filter-bar" method="get" action="/reviews">
        <label>
            <input type="text" name="author" placeholder="Author..."
                   value="${filter.author() != null ? filter.author() : ""}"/>
        </label>
        <label>
            <input type="number" name="ratingMin" placeholder="Min rating..." step="0.1" style="max-width: 130px;"
                   value="${filter.ratingMin() != null ? String.valueOf(filter.ratingMin()) : ""}"/>
        </label>
        <label>
            <input type="number" name="ratingMax" placeholder="Max rating..." step="0.1" style="max-width: 130px;"
                   value="${filter.ratingMax() != null ? String.valueOf(filter.ratingMax()) : ""}"/>
        </label>
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="/reviews" class="btn btn-ghost">Clear</a>
        <select name="sort" onchange="this.form.submit()" style="width: 150px;">
            <option value="reviewDate,desc" selected="${currentSort.equals("reviewDate,desc")}">Newest First</option>
            <option value="reviewDate,asc" selected="${currentSort.equals("reviewDate,asc")}">Oldest First</option>
            <option value="reviewRating,desc" selected="${currentSort.equals("reviewRating,desc")}">Highest Rated</option>
            <option value="reviewRating,asc" selected="${currentSort.equals("reviewRating,asc")}">Lowest Rated</option>
        </select>
    </form>

    @if(reviews.isEmpty())
        <div class="empty">
            <div class="empty-title">No reviews found</div>
            <p>Try adjusting your search filters.</p>
        </div>
    @else
        <div style="display: flex; flex-direction: column; gap: 1rem;">
            @for(ReviewDTO review : reviews.getContent())
                <div class="card">
                    <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 0.75rem;">
                        <div>
                            <a href="/movies/${review.movieId()}" style="font-family: var(--font-heading), serif; font-size: 1rem; color: var(--text);">
                                ${review.movieTitle()}
                            </a>
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
                        <p style="font-size: 0.875rem; color: var(--text-muted); line-height: 1.7;">
                            ${review.reviewText()}
                        </p>
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

        <div class="pagination">
            @if(!reviews.isFirst())
                <a href="?page=${reviews.getNumber() - 1}&size=${reviews.getSize()}${query}${sortQuery}">←</a>
            @endif

            @for(int i = 0; i < reviews.getTotalPages(); i++)
                @if(i == reviews.getNumber())
                    <span class="active">${i + 1}</span>
                @else
                    <a href="?page=${i}&size=${reviews.getSize()}${query}${sortQuery}">${i + 1}</a>
                @endif
            @endfor

            @if(!reviews.isLast())
                <a href="?page=${reviews.getNumber() + 1}&size=${reviews.getSize()}${query}${sortQuery}">→</a>
            @endif
        </div>
    @endif
`)